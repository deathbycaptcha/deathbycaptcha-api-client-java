package examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.DeathByCaptcha.Captcha;
import com.DeathByCaptcha.Client;
import com.DeathByCaptcha.HttpClient;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * Solves Google's reCAPTCHA v2 demo with Playwright and DeathByCaptcha.
 *
 * <p>Reads DBC_AUTHTOKEN or DBC_USERNAME/DBC_PASSWORD from the environment
 * or from a .env file in the working directory. The solved token is injected
 * into the page and the widget callback is called when it exists; the token
 * itself is never printed.</p>
 */
public class ExamplePlaywrightRecaptchaV2 {

    private static final String DEMO_URL = "https://www.google.com/recaptcha/api2/demo";

    private static boolean isHeadlessEnabled() {
        String value = System.getProperty(
                "playwright.headless",
                System.getenv().getOrDefault("DBC_PLAYWRIGHT_HEADLESS", "true"));
        return !"false".equalsIgnoreCase(value.trim());
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
        } catch (IOException e) {
            System.out.println("Failed reading .env: " + e.getMessage());
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

    private static Client createClient(Map<String, String> dotEnv) {
        String authToken = readConfig("DBC_AUTHTOKEN", dotEnv);
        String username = readConfig("DBC_USERNAME", dotEnv);
        String password = readConfig("DBC_PASSWORD", dotEnv);

        if (authToken != null && !authToken.isEmpty()) {
            return new HttpClient(authToken);
        }
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            return new HttpClient(username, password);
        }
        return null;
    }

    private static void injectToken(Page page, String token) {
        page.evaluate(
            "(token) => {\n" +
            "  const textarea = document.getElementById('g-recaptcha-response');\n" +
            "  if (!textarea) return false;\n" +
            "  textarea.style.display = 'block';\n" +
            "  textarea.value = token;\n" +
            "  textarea.innerHTML = token;\n" +
            "  textarea.dispatchEvent(new Event('input', { bubbles: true }));\n" +
            "  textarea.dispatchEvent(new Event('change', { bubbles: true }));\n" +
            "  const clients = window.___grecaptcha_cfg && window.___grecaptcha_cfg.clients;\n" +
            "  if (clients) {\n" +
            "    for (const key of Object.keys(clients)) {\n" +
            "      const client = clients[key];\n" +
            "      for (const inner of Object.keys(client)) {\n" +
            "        const item = client[inner];\n" +
            "        if (item && typeof item.callback === 'function') { item.callback(token); return true; }\n" +
            "        if (item && item.W && typeof item.W.callback === 'function') { item.W.callback(token); return true; }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "  return true;\n" +
            "}",
            token);
    }

    public static void main(String[] args) {
        Map<String, String> dotEnv = loadDotEnv();
        Client client = createClient(dotEnv);

        if (client == null) {
            System.out.println(
                "Missing credentials. Set DBC_AUTHTOKEN or DBC_USERNAME/DBC_PASSWORD in the environment or .env");
            return;
        }

        client.isVerbose = false;

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(isHeadlessEnabled()));

            try {
                BrowserContext context = browser.newContext();
                Page page = context.newPage();

                System.out.println("Navigating to " + DEMO_URL + "...");
                page.navigate(DEMO_URL);

                page.waitForSelector("#recaptcha-demo");
                String googleKey = page.getAttribute("#recaptcha-demo", "data-sitekey");
                if (googleKey == null || googleKey.trim().isEmpty()) {
                    System.out.println("Could not extract the reCAPTCHA sitekey from the page");
                    return;
                }
                System.out.println("Sitekey: " + googleKey);

                System.out.println("Balance: " + client.getBalance() + " US cents");

                JSONObject params = new JSONObject();
                params.put("googlekey", googleKey);
                params.put("pageurl", DEMO_URL);

                Captcha captcha = client.decode(params);
                if (captcha == null || captcha.text == null || captcha.text.trim().isEmpty()) {
                    System.out.println("No captcha solution was returned");
                    return;
                }
                System.out.println("Token received (" + captcha.text.length() + " characters)");

                injectToken(page, captcha.text);
                page.click("#recaptcha-demo-submit");

                page.waitForSelector(".recaptcha-success");
                System.out.println(page.textContent(".recaptcha-success"));
            } finally {
                browser.close();
            }
        } catch (Exception e) {
            System.out.println("Example failed: " + e.getMessage());
        } finally {
            client.close();
        }
    }
}
