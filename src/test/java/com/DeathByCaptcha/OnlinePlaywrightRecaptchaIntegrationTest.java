package com.DeathByCaptcha;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Assume;
import org.junit.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;

public class OnlinePlaywrightRecaptchaIntegrationTest {

    private static final String DEMO_URL = "https://www.google.com/recaptcha/api2/demo";

    @Test
    public void testPlaywrightRecaptchaHeadlessFlow() throws Exception {
        Map<String, String> dotEnv = loadDotEnv();
        String authToken = readConfig("DBC_AUTHTOKEN", dotEnv);
        String username = readConfig("DBC_USERNAME", dotEnv);
        String password = readConfig("DBC_PASSWORD", dotEnv);

        boolean hasToken = authToken != null && !authToken.isEmpty();
        boolean hasUserPass = username != null && !username.isEmpty() && password != null && !password.isEmpty();
        Assume.assumeTrue(
            "Skipping Playwright integration test: set DBC_AUTHTOKEN or DBC_USERNAME/DBC_PASSWORD",
            hasToken || hasUserPass
        );

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
            );
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            Client client = null;

            try {
                page.navigate(DEMO_URL, new Page.NavigateOptions().setTimeout(60000));

                page.waitForSelector("#recaptcha-demo", new Page.WaitForSelectorOptions().setTimeout(20000));
                String siteKey = page.getAttribute("#recaptcha-demo", "data-sitekey");
                assertNotNull("Expected reCAPTCHA site key", siteKey);
                assertFalse("Expected non-empty reCAPTCHA site key", siteKey.trim().isEmpty());

                client = hasToken ? new HttpClient(authToken) : new HttpClient(username, password);
                client.isVerbose = true;

                JSONObject params = new JSONObject();
                try {
                    params.put("googlekey", siteKey);
                    params.put("pageurl", DEMO_URL);
                } catch (JSONException e) {
                    fail("Failed building captcha parameters: " + e.getMessage());
                }

                Captcha captcha = null;
                try {
                    captcha = client.decode(params);
                } catch (IOException | InterruptedException e) {
                    fail("Failed solving captcha with DBC: " + e.getMessage());
                }

                assertNotNull("Expected solved captcha", captcha);
                assertNotNull("Expected captcha text", captcha.text);
                assertFalse("Expected non-empty captcha text", captcha.text.trim().isEmpty());

                page.evaluate(
                    "value => document.getElementById('g-recaptcha-response').value = value",
                    captcha.text
                );

                page.click("#recaptcha-demo-submit");

                page.waitForSelector(".recaptcha-success", new Page.WaitForSelectorOptions().setTimeout(15000));
                String successText = page.textContent(".recaptcha-success");
                assertNotNull("Expected success message text", successText);
                assertTrue(
                    "Expected 'Verification Success' message after form submit, got: " + successText,
                    successText.contains("Verification Success")
                );
            } finally {
                if (client != null) {
                    client.close();
                }
                browser.close();
            }
        }
    }

    private static Map<String, String> loadDotEnv() {
        Map<String, String> values = new HashMap<>();
        Path envPath = Paths.get(".env");

        if (!Files.exists(envPath)) {
            return values;
        }

        try (BufferedReader reader = Files.newBufferedReader(envPath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }

                int splitIndex = trimmed.indexOf('=');
                if (splitIndex <= 0) {
                    continue;
                }

                String key = trimmed.substring(0, splitIndex).trim();
                String value = trimmed.substring(splitIndex + 1).trim();

                if ((value.startsWith("\"") && value.endsWith("\"")) ||
                    (value.startsWith("'") && value.endsWith("'"))) {
                    value = value.substring(1, value.length() - 1);
                }

                values.put(key, value);
            }
        } catch (IOException ignored) {
        }

        return values;
    }

    private static String readConfig(String key, Map<String, String> dotEnv) {
        String fromEnv = System.getenv(key);
        if (fromEnv != null && !fromEnv.isEmpty()) {
            return fromEnv;
        }
        return dotEnv.get(key);
    }
}
