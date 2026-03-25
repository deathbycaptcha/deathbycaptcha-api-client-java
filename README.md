# [DeathByCaptcha](https://deathbycaptcha.com/)


<p align="center">
  <a href="https://github.com/deathbycaptcha/deathbycaptcha-api-client-python"><img alt="Python" src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white"></a>
  <a href="https://github.com/deathbycaptcha/deathbycaptcha-api-client-nodejs"><img alt="Node.js" src="https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=nodedotjs&logoColor=white"></a>
  <a href="https://github.com/deathbycaptcha/deathbycaptcha-api-client-dotnet"><img alt=".NET" src="https://img.shields.io/badge/.NET-512BD4?style=for-the-badge&logo=dotnet&logoColor=white"></a>
  <a href="https://github.com/deathbycaptcha/deathbycaptcha-api-client-java"><img alt="Java" src="https://img.shields.io/badge/%E2%80%BA-Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white&labelColor=555555"></a>
  <a href="https://github.com/deathbycaptcha/deathbycaptcha-api-client-php"><img alt="PHP" src="https://img.shields.io/badge/PHP-777BB4?style=for-the-badge&logo=php&logoColor=white"></a>
  <a href="https://github.com/deathbycaptcha/deathbycaptcha-api-client-perl"><img alt="Perl" src="https://img.shields.io/badge/Perl-39457E?style=for-the-badge&logo=perl&logoColor=white"></a>
  <a href="https://github.com/deathbycaptcha/deathbycaptcha-api-client-c11"><img alt="C" src="https://img.shields.io/badge/C-A8B9CC?style=for-the-badge&logo=c&logoColor=black"></a>
  <a href="https://github.com/deathbycaptcha/dbc_api_autoit"><img alt="AutoIt" src="https://img.shields.io/badge/AutoIt-1C3552?style=for-the-badge"></a>
</p>


## 📖 Introduction

The [DeathByCaptcha](https://deathbycaptcha.com) Java client is a full-featured **captcha solver API** wrapper for Java applications. It provides a simple, well-documented interface to the [DeathByCaptcha](https://deathbycaptcha.com) solving service — including support for **captcha bypass for Selenium** browser automation flows where CAPTCHAs must be solved before form submission or navigation can continue. It supports both the HTTPS API (encrypted transport — recommended when security is a priority) and the socket-based API (faster and lower latency, recommended for high-throughput production workloads).

Key features:

- 🧩 Send image, audio and modern token-based CAPTCHA types (reCAPTCHA v2/v3, Turnstile, GeeTest, etc.).
- 🔄 Unified client API across HTTP and socket transports — switching implementations is straightforward.
- 🔐 Built-in support for proxies, timeouts and advanced token parameters for modern CAPTCHA flows.
- 🧵 Thread-safe clients — safe to share between multiple threads or pool for high-concurrency workloads.

Quick start example (HTTP):

```java
import com.DeathByCaptcha.Client;
import com.DeathByCaptcha.HttpClient;
import com.DeathByCaptcha.Captcha;

Client client = new HttpClient("your_username", "your_password");
Captcha captcha = client.decode("path/to/captcha.jpg");
if (captcha != null) {
    System.out.println(captcha.text);
}
```

> **🚌 Transport options:** Use `HttpClient` for encrypted HTTPS communication — credentials and data travel over TLS. Use `SocketClient` for lower latency and higher throughput — it is faster but communicates over a plain TCP connection to `api.dbcapi.me` on ports `8123–8130`.

---

### Tests Status

[![Java 25](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/java25-tests.yml/badge.svg)](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/java25-tests.yml)
[![Coverage](https://img.shields.io/endpoint?url=https%3A%2F%2Fdeathbycaptcha.github.io%2Fdeathbycaptcha-api-client-java%2Fcoverage-badge.json&cacheSeconds=300)](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/coverage-tests.yml)
[![Integration API](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/integration-tests.yml/badge.svg)](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/integration-tests.yml)
[![Maven Online](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/maven-online-tests.yml/badge.svg)](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/maven-online-tests.yml)

---

## 🗂️ Index

- [Installation](#installation)
    - [From Maven Central (Recommended)](#from-maven-central-recommended)
    - [From GitHub Repository](#from-github-repository)
- [How to Use DBC API Clients](#how-to-use-dbc-api-clients)
    - [Common Clients' Interface](#common-clients-interface)
    - [Available Methods](#api-methods)
- [Credentials & Configuration](#credentials--configuration)
    - [Quick Setup](#quick-setup)
- [CAPTCHA Types Quick Reference & Examples](#captcha-types-quick-reference--examples)
    - [Quick Start](#quick-start)
    - [Type Reference](#type-reference)
    - [Per-Type Code Snippets](#per-type-code-snippets)
- [CAPTCHA Types Extended Reference](#captcha-types-extended-reference)
    - [reCAPTCHA Image-Based API — Deprecated (Types 2 & 3)](#recaptcha-image-based-api--deprecated-types-2--3)
    - [reCAPTCHA Token API (v2 & v3)](#recaptcha-token-api-v2--v3)
    - [reCAPTCHA v2 API FAQ](#recaptcha-v2-api-faq)
    - [What is reCAPTCHA v3?](#what-is-recaptcha-v3)
    - [reCAPTCHA v3 API FAQ](#recaptcha-v3-api-faq)
    - [Amazon WAF API (Type 16)](#amazon-waf-api-type-16)
    - [Cloudflare Turnstile API (Type 12)](#cloudflare-turnstile-api-type-12)
    - [Featured Sample: Selenium reCAPTCHA v2](#-featured-sample-selenium-recaptcha-v2)
- [Project Structure](#project-structure)
- [Continuous Integration](#continuous-integration)
- [License](#license)
- [Responsible Use](#responsible-use)

<a id="installation"></a>
## 🛠️ Installation

<a id="from-maven-central-recommended"></a>
### 📦 From Maven Central (Recommended)

Add the dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>io.github.deathbycaptcha</groupId>
  <artifactId>deathbycaptcha-java-library</artifactId>
  <version>4.7.0</version>
</dependency>
```

**Note:** Maven Central publishes only the core library artifact. Runnable samples are repository-only and run with Maven profile `samples`.

<a id="from-github-repository"></a>
### 🐙 From GitHub Repository

For the latest development version or to contribute:

```bash
git clone https://github.com/deathbycaptcha/deathbycaptcha-api-client-java.git
cd deathbycaptcha-api-client-java
mvn clean compile
```

To compile with runnable samples:

```bash
mvn -Psamples clean compile
```

<a id="how-to-use-dbc-api-clients"></a>
## 🚀 How to Use DBC API Clients

<a id="common-clients-interface"></a>
### 🔌 Common Clients' Interface

All clients must be instantiated with your DeathByCaptcha credentials — either *username* and *password*, or an *authtoken* (available in the DBC user panel). Replace `HttpClient` with `SocketClient` to use the socket transport instead.

```java
import com.DeathByCaptcha.Client;
import com.DeathByCaptcha.HttpClient;
import com.DeathByCaptcha.SocketClient;

// Username + password (HTTPS transport — encrypted, recommended when security matters)
Client client = new HttpClient(username, password);

// Username + password (socket transport — faster, lower latency, recommended for high throughput)
// Client client = new SocketClient(username, password);

// Authtoken only
// Client client = new HttpClient(authtoken);
```

| Transport | Class | Best for |
|---|---|---|
| HTTPS | `HttpClient` | Encrypted TLS transport — safer for credential handling and network-sensitive environments |
| Socket | `SocketClient` | Plain TCP — faster and lower latency, recommended for high-throughput production workloads |

All clients are **thread-safe** and share the same interface. Below is a summary of every available method.

<a id="api-methods"></a>

| Method | Signature | Returns | Description |
|---|---|---|---|
| `decode()` | `decode(String filename)` | `Captcha` or `null` | Upload image and poll until solved or timed out. Preferred method for most integrations. |
| `decode()` | `decode(int type, ...)` | `Captcha` or `null` | Upload token-based CAPTCHA by type with parameters. |
| `upload()` | `upload(String filename)` | `Captcha` or `null` | Upload a CAPTCHA for solving without waiting. |
| `getCaptcha()` | `getCaptcha(int captchaId)` | `Captcha` or `null` | Fetch status and result of a previously uploaded CAPTCHA by its numeric ID. |
| `report()` | `report(Captcha captcha)` | `boolean` | Report a CAPTCHA as incorrectly solved to request a refund. Only report genuine errors. |
| `getBalance()` | `getBalance()` | `double` | Return the current account balance in US cents. |

### 📬 CAPTCHA Result Object

All methods that return a solved CAPTCHA return a `com.DeathByCaptcha.Captcha` object with the following properties:

| Property / Method | Type | Description |
|---|---|---|
| `id` | `int` | Numeric CAPTCHA ID assigned by DBC |
| `text` | `String` | Solved text or token (the value you inject into the page) |
| `isUploaded()` | `boolean` | Whether the CAPTCHA was uploaded |
| `isSolved()` | `boolean` | Whether the CAPTCHA was solved |
| `isCorrect()` | `boolean` | Whether DBC considers the solution correct |

```java
// Example result
if (captcha != null) {
    System.out.println("CAPTCHA ID: " + captcha.id);
    System.out.println("Solution:   " + captcha.text);
    System.out.println("Correct:    " + captcha.isCorrect());
}
```

### 💡 Full Usage Example

```java
import com.DeathByCaptcha.Client;
import com.DeathByCaptcha.HttpClient;
import com.DeathByCaptcha.Captcha;

Client client = new HttpClient(username, password);

try {
    System.out.println("Balance: " + client.getBalance() + " US cents");

    Captcha captcha = client.decode("path/to/captcha.jpg");
    if (captcha != null) {
        System.out.println("Solved CAPTCHA " + captcha.id + ": " + captcha.text);

        // Report only if you are certain the solution is wrong:
        // client.report(captcha);
    }
} catch (com.DeathByCaptcha.AccessDeniedException e) {
    System.out.println("Access denied — check your credentials and/or balance");
}
```

<a id="credentials--configuration"></a>
## 🔑 Credentials & Configuration

For detailed information about setting up credentials for different environments, see [docs/getting-started.md](docs/getting-started.md):

- **Local Development**: Use `.env` file with `run-tests.sh`
- **GitHub Actions**: Configure repository secrets (`DBC_USERNAME`, `DBC_PASSWORD`)
- **GitLab CI**: Configure project CI/CD variables

<a id="quick-setup"></a>
### ⚡ Quick Setup

```bash
# ① Clone the repo
git clone https://github.com/deathbycaptcha/deathbycaptcha-api-client-java.git
cd deathbycaptcha-api-client-java

# ② Set credentials
export DBC_USERNAME="your_username"
export DBC_PASSWORD="your_password"

# ③ Run balance check to validate setup
mvn -Psamples exec:java \
  -Dexec.mainClass="examples.ExampleGetBalance" \
  -Dexec.args="your_username your_password HTTP"

# ④ Run tests locally
./run-tests.sh
```

See [docs/getting-started.md](docs/getting-started.md) for complete setup instructions for each environment.

<a id="captcha-types-quick-reference--examples"></a>
## 🧩 CAPTCHA Types Quick Reference & Examples

<a id="quick-start"></a>
### 🏁 Quick Start

1. **📦 Install the library** (see [Installation](#installation))
2. **📂 Navigate to the repo root** and run the sample for the type you need:

```bash
mvn -Psamples exec:java \
  -Dexec.mainClass="examples.ExampleNormalCaptcha"

# Balance check:
mvn -Psamples exec:java \
  -Dexec.mainClass="examples.ExampleGetBalance" \
  -Dexec.args="your_username your_password HTTP"
```

Before running any sample, add your DBC credentials inside the file:

```java
String username = "your_username";
String password = "your_password";
```

<a id="type-reference"></a>
### 📋 Type Reference

The table below maps every supported type to its use case, a code snippet, and the corresponding example file in `samples/`.

| Type ID | CAPTCHA Type | Use Case | Quick Use | Java Sample |
| --- | --- | --- | --- | --- |
| 0 | Standard Image | Basic image CAPTCHA | [snippet](#sample-type-0-standard-image) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleNormalCaptcha.java) |
| 2 | ~~reCAPTCHA Coordinates~~ | Deprecated — do not use for new integrations | — | — |
| 3 | ~~reCAPTCHA Image Group~~ | Deprecated — do not use for new integrations | — | — |
| 4 | reCAPTCHA v2 Token | reCAPTCHA v2 token solving | [snippet](#sample-type-4-recaptcha-v2-token) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleRecaptchaV2.java) |
| 5 | reCAPTCHA v3 Token | reCAPTCHA v3 with risk scoring | [snippet](#sample-type-5-recaptcha-v3-token) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleRecaptchaV3.java) |
| 25 | reCAPTCHA v2 Enterprise | reCAPTCHA v2 Enterprise tokens | [snippet](#sample-type-25-recaptcha-v2-enterprise) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleRecaptchaV2Enterprise.java) |
| 8 | GeeTest v3 | Geetest v3 verification | [snippet](#sample-type-8-geetest-v3) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleGeetestV3.java) |
| 9 | GeeTest v4 | Geetest v4 verification | [snippet](#sample-type-9-geetest-v4) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleGeetestV4.java) |
| 11 | Text CAPTCHA | Text-based question solving | [snippet](#sample-type-11-text-captcha) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleTextcaptcha.java) |
| 12 | Cloudflare Turnstile | Cloudflare Turnstile token | [snippet](#sample-type-12-cloudflare-turnstile) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleTurnstile.java) |
| 13 | Audio CAPTCHA | Audio CAPTCHA solving | [snippet](#sample-type-13-audio-captcha) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleAudioCaptcha.java) |
| 14 | Lemin | Lemin CAPTCHA | [snippet](#sample-type-14-lemin) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleLemin.java) |
| 15 | Capy | Capy CAPTCHA | [snippet](#sample-type-15-capy) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleCapy.java) |
| 16 | Amazon WAF | Amazon WAF verification | [snippet](#sample-type-16-amazon-waf) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleAmazonWaf.java) |
| 17 | Siara | Siara CAPTCHA | [snippet](#sample-type-17-siara) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleSiara.java) |
| 18 | MTCaptcha | Mtcaptcha CAPTCHA | [snippet](#sample-type-18-mtcaptcha) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleMtcaptcha.java) |
| 19 | Cutcaptcha | Cutcaptcha CAPTCHA | [snippet](#sample-type-19-cutcaptcha) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleCutcaptcha.java) |
| 20 | Friendly Captcha | Friendly Captcha | [snippet](#sample-type-20-friendly-captcha) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleFriendlycaptcha.java) |
| 21 | DataDome | Datadome verification | [snippet](#sample-type-21-datadome) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleDatadome.java) |
| 23 | Tencent | Tencent CAPTCHA | [snippet](#sample-type-23-tencent) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleTencent.java) |
| 24 | ATB | ATB CAPTCHA | [snippet](#sample-type-24-atb) | [open](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleAtb.java) |

<a id="per-type-code-snippets"></a>
### 📝 Per-Type Code Snippets

Minimal usage snippet for each supported type. Use these as a starting point and refer to the full example files in `samples/` for complete implementations.

<a id="sample-type-0-standard-image"></a>
#### 🖼️ Sample Type 0: Standard Image
Official description: [Supported CAPTCHAs](https://deathbycaptcha.com/api#supported_captchas)
Full sample: [ExampleNormalCaptcha.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleNormalCaptcha.java)

```java
Captcha captcha = client.decode("src/images/test.jpg");
```

---

<a id="sample-type-4-recaptcha-v2-token"></a>
#### 🤖 Sample Type 4: reCAPTCHA v2 Token
Official description: [reCAPTCHA Token API (v2)](https://deathbycaptcha.com/api/newtokenrecaptcha#token-v2)
Full sample: [ExampleRecaptchaV2.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleRecaptchaV2.java)

```java
String proxy = "http://user:password@127.0.0.1:1234";
String proxytype = "HTTP";
String googlekey = "6LfW6wATAAAAAHLqO2pb8bDBahxlMxNdo9g947u9";
String pageurl = "https://recaptcha-demo.appspot.com/recaptcha-v2-checkbox.php";
Captcha captcha = client.decode(4, proxy, proxytype, googlekey, pageurl);
```

---

<a id="sample-type-5-recaptcha-v3-token"></a>
#### 🤖 Sample Type 5: reCAPTCHA v3 Token
Official description: [reCAPTCHA v3](https://deathbycaptcha.com/api/newtokenrecaptcha#reCAPTCHAv3)
Full sample: [ExampleRecaptchaV3.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleRecaptchaV3.java)

```java
String proxy = "http://user:password@127.0.0.1:1234";
String proxytype = "HTTP";
String googlekey = "6LdyC2cUAAAAACGuDKpXeDorzUDWXmdqeg-xy696";
String pageurl = "https://recaptcha-demo.appspot.com/recaptcha-v3-request-scores.php";
String action = "examples/v3scores";
double min_score = 0.3;
Captcha captcha = client.decode(proxy, proxytype, googlekey, pageurl, action, min_score);
```

---

<a id="sample-type-25-recaptcha-v2-enterprise"></a>
#### 🏢 Sample Type 25: reCAPTCHA v2 Enterprise
Official description: [reCAPTCHA v2 Enterprise](https://deathbycaptcha.com/api/newtokenrecaptcha#reCAPTCHAv2Enterprise)
Full sample: [ExampleRecaptchaV2Enterprise.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleRecaptchaV2Enterprise.java)

```java
String proxy = "http://user:password@127.0.0.1:1234";
String proxytype = "HTTP";
String googlekey = "6LfW6wATAAAAAHLqO2pb8bDBahxlMxNdo9g947u9";
String pageurl = "https://recaptcha-demo.appspot.com/recaptcha-v2-checkbox.php";
Captcha captcha = client.decode(25, proxy, proxytype, googlekey, pageurl);
```

---

<a id="sample-type-8-geetest-v3"></a>
#### 🧩 Sample Type 8: GeeTest v3
Official description: [GeeTest](https://deathbycaptcha.com/api/geetest)
Full sample: [ExampleGeetestV3.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleGeetestV3.java)

```java
JSONObject params = new JSONObject();
params.put("proxy", "http://user:password@127.0.0.1:1234");
params.put("proxytype", "HTTP");
params.put("gt", "gt_value");
params.put("challenge", "challenge_value");
params.put("pageurl", "https://target.com");
Captcha captcha = client.decode(8, params);
```

---

<a id="sample-type-9-geetest-v4"></a>
#### 🧩 Sample Type 9: GeeTest v4
Official description: [GeeTest](https://deathbycaptcha.com/api/geetest)
Full sample: [ExampleGeetestV4.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleGeetestV4.java)

```java
JSONObject params = new JSONObject();
params.put("proxy", "http://user:password@127.0.0.1:1234");
params.put("proxytype", "HTTP");
params.put("captcha_id", "captcha_id_value");
params.put("pageurl", "https://target.com");
Captcha captcha = client.decode(9, params);
```

---

<a id="sample-type-11-text-captcha"></a>
#### 💬 Sample Type 11: Text CAPTCHA
Official description: [Text CAPTCHA](https://deathbycaptcha.com/api/textcaptcha)
Full sample: [ExampleTextcaptcha.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleTextcaptcha.java)

```java
Captcha captcha = client.decode(11, "What is two plus two?", 0);
```

---

<a id="sample-type-12-cloudflare-turnstile"></a>
#### ☁️ Sample Type 12: Cloudflare Turnstile
Official description: [Cloudflare Turnstile](https://deathbycaptcha.com/api/turnstile)
Full sample: [ExampleTurnstile.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleTurnstile.java)

```java
String proxy = "http://user:password@127.0.0.1:1234";
String proxytype = "HTTP";
String sitekey = "0x4AAAAAAAGlwMzq_9z6S9Mh";
String pageurl = "https://target.com";
Captcha captcha = client.decode(12, proxy, proxytype, sitekey, pageurl);
```

---

<a id="sample-type-13-audio-captcha"></a>
#### 🔊 Sample Type 13: Audio CAPTCHA
Official description: [Audio CAPTCHA](https://deathbycaptcha.com/api/audio)
Full sample: [ExampleAudioCaptcha.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleAudioCaptcha.java)

```java
byte[] fileContent = Files.readAllBytes(Paths.get("images/audio.mp3"));
String audioBase64 = Base64.getEncoder().encodeToString(fileContent);
Captcha captcha = client.decode(13, audioBase64, "en");
```

---

<a id="sample-type-14-lemin"></a>
#### 🔵 Sample Type 14: Lemin
Official description: [Lemin](https://deathbycaptcha.com/api/lemin)
Full sample: [ExampleLemin.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleLemin.java)

```java
JSONObject params = new JSONObject();
params.put("proxy", "http://user:password@127.0.0.1:1234");
params.put("proxytype", "HTTP");
params.put("captchaid", "CROPPED_xxx");
params.put("pageurl", "https://target.com");
Captcha captcha = client.decode(14, params);
```

---

<a id="sample-type-15-capy"></a>
#### 🏴 Sample Type 15: Capy
Official description: [Capy](https://deathbycaptcha.com/api/capy)
Full sample: [ExampleCapy.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleCapy.java)

```java
JSONObject params = new JSONObject();
params.put("proxy", "http://user:password@127.0.0.1:1234");
params.put("proxytype", "HTTP");
params.put("captchakey", "PUZZLE_xxx");
params.put("api_server", "https://api.capy.me/");
params.put("pageurl", "https://target.com");
Captcha captcha = client.decode(15, params);
```

---

<a id="sample-type-16-amazon-waf"></a>
#### 🛡️ Sample Type 16: Amazon WAF
Official description: [Amazon WAF](https://deathbycaptcha.com/api/amazonwaf)
Full sample: [ExampleAmazonWaf.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleAmazonWaf.java)

```java
JSONObject params = new JSONObject();
params.put("proxy", "http://user:password@127.0.0.1:1234");
params.put("proxytype", "HTTP");
params.put("sitekey", "AQID...");
params.put("pageurl", "https://efw47fpad9.execute-api.us-east-1.amazonaws.com/latest");
params.put("iv", "CgAFRjIw2vAAABSM");
params.put("context", "zPT0jOl1...");
Captcha captcha = client.decode(16, params);
```

---

<a id="sample-type-17-siara"></a>
#### 🔍 Sample Type 17: Siara
Official description: [Siara](https://deathbycaptcha.com/api/siara)
Full sample: [ExampleSiara.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleSiara.java)

```java
JSONObject params = new JSONObject();
params.put("proxy", "http://user:password@127.0.0.1:1234");
params.put("proxytype", "HTTP");
params.put("slideurlid", "slide_master_url_id");
params.put("pageurl", "https://target.com");
params.put("useragent", "Mozilla/5.0");
Captcha captcha = client.decode(17, params);
```

---

<a id="sample-type-18-mtcaptcha"></a>
#### 🔒 Sample Type 18: MTCaptcha
Official description: [MTCaptcha](https://deathbycaptcha.com/api/mtcaptcha)
Full sample: [ExampleMtcaptcha.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleMtcaptcha.java)

```java
JSONObject params = new JSONObject();
params.put("proxy", "http://user:password@127.0.0.1:1234");
params.put("proxytype", "HTTP");
params.put("sitekey", "MTPublic-xxx");
params.put("pageurl", "https://target.com");
Captcha captcha = client.decode(18, params);
```

---

<a id="sample-type-19-cutcaptcha"></a>
#### ✂️ Sample Type 19: Cutcaptcha
Official description: [Cutcaptcha](https://deathbycaptcha.com/api/cutcaptcha)
Full sample: [ExampleCutcaptcha.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleCutcaptcha.java)

```java
JSONObject params = new JSONObject();
params.put("proxy", "http://user:password@127.0.0.1:1234");
params.put("proxytype", "HTTP");
params.put("apikey", "api_key");
params.put("miserykey", "misery_key");
params.put("pageurl", "https://target.com");
Captcha captcha = client.decode(19, params);
```

---

<a id="sample-type-20-friendly-captcha"></a>
#### 💚 Sample Type 20: Friendly Captcha
Official description: [Friendly Captcha](https://deathbycaptcha.com/api/friendly)
Full sample: [ExampleFriendlycaptcha.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleFriendlycaptcha.java)

```java
JSONObject params = new JSONObject();
params.put("proxy", "http://user:password@127.0.0.1:1234");
params.put("proxytype", "HTTP");
params.put("sitekey", "FCMG...");
params.put("pageurl", "https://target.com");
Captcha captcha = client.decode(20, params);
```

---

<a id="sample-type-21-datadome"></a>
#### 🛡️ Sample Type 21: DataDome
Official description: [DataDome](https://deathbycaptcha.com/api/datadome)
Full sample: [ExampleDatadome.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleDatadome.java)

```java
JSONObject params = new JSONObject();
params.put("proxy", "http://user:password@127.0.0.1:1234");
params.put("proxytype", "HTTP");
params.put("pageurl", "https://target.com");
params.put("captcha_url", "https://target.com/captcha");
Captcha captcha = client.decode(21, params);
```

---

<a id="sample-type-23-tencent"></a>
#### 🇨🇳 Sample Type 23: Tencent
Official description: [Tencent](https://deathbycaptcha.com/api/tencent)
Full sample: [ExampleTencent.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleTencent.java)

```java
JSONObject params = new JSONObject();
params.put("proxy", "http://user:password@127.0.0.1:1234");
params.put("proxytype", "HTTP");
params.put("appid", "appid");
params.put("pageurl", "https://target.com");
Captcha captcha = client.decode(23, params);
```

---

<a id="sample-type-24-atb"></a>
#### 🏷️ Sample Type 24: ATB
Official description: [ATB](https://deathbycaptcha.com/api/atb)
Full sample: [ExampleAtb.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleAtb.java)

```java
JSONObject params = new JSONObject();
params.put("proxy", "http://user:password@127.0.0.1:1234");
params.put("proxytype", "HTTP");
params.put("appid", "appid");
params.put("apiserver", "https://cap.aisecurius.com");
params.put("pageurl", "https://target.com");
Captcha captcha = client.decode(24, params);
```

<a id="captcha-types-extended-reference"></a>
## 📚 CAPTCHA Types Extended Reference

Full API-level documentation for selected CAPTCHA types: parameter references, payload schemas, request/response formats, token lifespans, and integration notes.

<a id="recaptcha-image-based-api--deprecated-types-2--3"></a>
### ⛔ reCAPTCHA Image-Based API — Deprecated (Types 2 & 3)

> ⚠️ **Deprecated.** Types 2 (Coordinates) and 3 (Image Group) are legacy image-based reCAPTCHA challenge methods that are no longer used. Do not use them for new integrations — use the [reCAPTCHA Token API (v2 & v3)](#recaptcha-token-api-v2--v3) instead.

---

<a id="recaptcha-token-api-v2--v3"></a>
### 🔐 reCAPTCHA Token API (v2 & v3)

The Token-based API solves reCAPTCHA challenges by returning a token you inject directly into the page form, rather than clicking images. Given a site URL and site key, DBC solves the challenge on its side and returns a token valid for one submission.

- **Token Image API**: Provided a site URL and site key, the API returns a token that you use to submit the form on the page with the reCAPTCHA challenge.

---

<a id="recaptcha-v2-api-faq"></a>
### ❓ reCAPTCHA v2 API FAQ

**What's the Token Image API URL?**
To use the Token Image API you will have to send a HTTP POST Request to <http://api.dbcapi.me/api/captcha>

**What are the POST parameters for the Token image API?**
-   **`username`**: Your DBC account username
-   **`password`**: Your DBC account password
-   **`type`=4**: Type 4 specifies this is the reCAPTCHA v2 Token API
-   **`token_params`=json(payload)**: the data to access the recaptcha challenge

json payload structure:
    -   **`proxy`**: your proxy url and credentials (if any). Examples:
        -   <http://127.0.0.1:3128>
        -   <http://user:password@127.0.0.1:3128>
    -   **`proxytype`**: your proxy connection protocol. Example: `HTTP`
    -   **`googlekey`**: the google recaptcha site key of the website with the recaptcha. Example: `6Le-wvkSAAAAAPBMRTvw0Q4Muexq9bi0DJwx_mJ-`
    -   **`pageurl`**: the url of the page with the recaptcha challenges. This url has to include the path in which the recaptcha is loaded.
    -   **`data-s`**: Only required for Google search tokens. Use the data-s value inside the google search response html. For regular tokens don't use this parameter.

The **`proxy`** parameter is optional, but we strongly recommend to use one to prevent token rejection by the provided page due to inconsistencies between the IP that solved the captcha (ours if no proxy is provided) and the IP that submitted the token for verification (yours).

**Note**: If **`proxy`** is provided, **`proxytype`** is a required parameter.

Full example of **`token_params`**:
```json
{
  "proxy": "http://127.0.0.1:3128",
  "proxytype": "HTTP",
  "googlekey": "6LfW6wATAAAAAHLqO2pb8bDBahxlMxNdo9g947u9",
  "pageurl": "https://recaptcha-demo.appspot.com/recaptcha-v2-checkbox.php"
}
```

**What's the response from the Token image API?**
The token image API response has the same structure as regular captchas' response. The token will come in the `text` field of the `Captcha` object. It's valid for one use and has a 2 minute lifespan.

---

<a id="what-is-recaptcha-v3"></a>
### 🔎 What is reCAPTCHA v3?

This API extends the reCAPTCHA v2 Token API with two additional parameters: `action` and **minimal score (`min_score`)**.

reCAPTCHA v3 returns a score from each user, that evaluates if the user is a bot or human. Lower scores near to 0 are identified as bot.

The `action` parameter at reCAPTCHA v3 is an additional data used to separate different captcha validations like **login**, **register**, **sales**, etc.

---

<a id="recaptcha-v3-api-faq"></a>
### ❓ reCAPTCHA v3 API FAQ

**What is `action` in reCAPTCHA v3?**
Is a new parameter that allows processing user actions on the website differently. To find this we need to inspect the javascript code of the website looking for call of `grecaptcha.execute` function. The API will use the `"verify"` default value if we won't provide `action` in our request.

**What is `min_score` in reCAPTCHA v3 API?**
The minimal score needed for the captcha resolution. We recommend using the `0.3` min-score value, as scores higher than `0.3` are hard to get.

Full example of **`token_params`**:
```json
{
  "proxy": "http://127.0.0.1:3128",
  "proxytype": "HTTP",
  "googlekey": "6LdyC2cUAAAAACGuDKpXeDorzUDWXmdqeg-xy696",
  "pageurl": "https://recaptcha-demo.appspot.com/recaptcha-v3-request-scores.php",
  "action": "examples/v3scores",
  "min_score": 0.3
}
```

**What's the response from reCAPTCHA v3 API?**
The solution will come in the `text` field of the `Captcha` object. It's valid for one use and has a 1 minute lifespan.

---

<a id="amazon-waf-api-type-16"></a>
### 🛡️ Amazon WAF API (Type 16)

Amazon WAF Captcha is part of the Intelligent Threat Mitigation system within Amazon AWS. DBC solves it by returning a token you set as the `aws-waf-token` cookie on the target page.

- **Official documentation:** [deathbycaptcha.com/api/amazonwaf](https://deathbycaptcha.com/api/amazonwaf)
- **Java sample:** [ExampleAmazonWaf.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleAmazonWaf.java)

**`waf_params` payload fields:**

| Parameter | Required | Description |
|---|---|---|
| `proxy` | Optional\* | Proxy URL with credentials |
| `proxytype` | Required if proxy set | Proxy protocol. Currently only `HTTP` is supported. |
| `sitekey` | Required | Amazon WAF site key found in the page's captcha script |
| `pageurl` | Required | Full URL of the page showing the challenge |
| `iv` | Required | Value of the `iv` parameter found in the captcha script |
| `context` | Required | Value of the `context` parameter found in the captcha script |
| `challengejs` | Optional | URL of the `challenge.js` script referenced on the page |
| `captchajs` | Optional | URL of the `captcha.js` script referenced on the page |

**Response:** The API returns a token valid for one use with a 1-minute lifespan. Set it as the `aws-waf-token` cookie on the target page before submitting the form.

---

<a id="cloudflare-turnstile-api-type-12"></a>
### 🌐 Cloudflare Turnstile API (Type 12)

Cloudflare Turnstile is a CAPTCHA alternative that protects pages without requiring user interaction in most cases. DBC solves it by returning a token you inject into the target form.

- **Official documentation:** [deathbycaptcha.com/api/turnstile](https://deathbycaptcha.com/api/turnstile)
- **Java sample:** [ExampleTurnstile.java](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/blob/master/samples/src/main/java/examples/ExampleTurnstile.java)

**`turnstile_params` payload fields:**

| Field | Required | Description |
|---|---|---|
| `proxy` | Optional | Proxy URL with optional credentials |
| `proxytype` | Required if `proxy` set | Proxy connection protocol. Currently only `HTTP` is supported. |
| `sitekey` | Required | The Turnstile site key found in `data-sitekey` attribute |
| `pageurl` | Required | Full URL of the page hosting the Turnstile challenge |
| `action` | Optional | Value of the `data-action` attribute or the `action` option passed to `turnstile.render` |

**Response:** The API returns a token string valid for one use with a 2-minute lifespan. Submit it via the `input[name="cf-turnstile-response"]` field or pass it to the callback defined in `turnstile.render`.

---

<a id="-featured-sample-selenium-recaptcha-v2"></a>
### ⭐ Featured Sample: Selenium reCAPTCHA v2

This repository includes an integrated Selenium sample at:

- `samples/src/main/java/examples/ExampleSeleniumRecaptchaV2.java`

Use this sample when you need a browser-automation flow that extracts `sitekey`, requests a token using DeathByCaptcha (type 4), injects it into the page, and submits the form.

Quick run:

```bash
mvn -Psamples exec:java \
  -Dexec.mainClass="examples.ExampleSeleniumRecaptchaV2" \
  -Dselenium.headless=true
```

See detailed usage in [docs/selenium-integration.md](docs/selenium-integration.md).

<a id="project-structure"></a>
## 🗂️ Project Structure

- `src/main/java/com/DeathByCaptcha/`
  - Core client library (`Client`, `HttpClient`, `SocketClient`, models, and exceptions).
- `samples/src/main/java/examples/`
  - Runnable examples for all supported CAPTCHA types, balance check, and Selenium integration.
- `src/test/java/com/DeathByCaptcha/`
  - Unit tests for core client functionality.
  - Integration tests for live API validation.
  - Maven Central artifact availability tests.
- `.github/workflows/`
  - `java17-tests.yml`, `java21-tests.yml`, `java25-tests.yml`: Per-version Java testing.
  - `coverage-tests.yml`: Code coverage reporting with JaCoCo.
  - `integration-tests.yml`: Live API integration tests.
  - `selenium-integration-tests.yml`: Headless Selenium reCAPTCHA integration tests.
  - `maven-online-tests.yml`: Maven Central availability tests.
  - `publish-maven-central.yml`: Automated publishing on release.
- `docs/`
  - `getting-started.md`: Installation and setup guide.
  - `samples.md`: How to compile and run repository samples with `-Psamples`.
  - `library-usage.md`: API usage and supported CAPTCHA types.
  - `selenium-integration.md`: Selenium automation guide.
  - `ci-and-coverage.md`: CI/CD configuration details.

<a id="continuous-integration"></a>
## 🔬 Continuous Integration

This project is configured for automated testing on multiple platforms:

### GitHub Actions

- Tested Java versions: 17 (LTS), 21 (LTS), 25 (latest LTS) in independent workflows.
- Coverage runs in a dedicated workflow: `coverage-tests.yml`.
- API integration tests run in `integration-tests.yml`.
- Selenium integration tests run in `selenium-integration-tests.yml`.
- Maven online tests run in `maven-online-tests.yml`.
- Maven Central publish runs on Release publish: `publish-maven-central.yml`.

### GitLab CI/CD

- Java LTS matrix (17/21/25) with Maven and JaCoCo.
- Supports credentials via CI variables and local `.env` for `gitlab-ci-local`.

### Workflow status

| Type | Workflow | Status |
|------|----------|--------|
| Java LTS | Java 17 | [![Java 17](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/java17-tests.yml/badge.svg)](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/java17-tests.yml) |
| Java LTS | Java 21 | [![Java 21](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/java21-tests.yml/badge.svg)](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/java21-tests.yml) |
| Java LTS | Java 25 | [![Java 25](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/java25-tests.yml/badge.svg)](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/java25-tests.yml) |
| Quality | Coverage | [![Coverage](https://img.shields.io/endpoint?url=https%3A%2F%2Fdeathbycaptcha.github.io%2Fdeathbycaptcha-api-client-java%2Fcoverage-badge.json&cacheSeconds=300)](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/coverage-tests.yml) |
| Integration | API integration | [![Integration API](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/integration-tests.yml/badge.svg)](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/integration-tests.yml) |
| Integration | Selenium integration | [![Selenium Integration](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/selenium-integration-tests.yml/badge.svg)](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/selenium-integration-tests.yml) |
| Integration | Maven online | [![Maven Online](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/maven-online-tests.yml/badge.svg)](https://github.com/deathbycaptcha/deathbycaptcha-api-client-java/actions/workflows/maven-online-tests.yml) |

### Badge Details

- **Java 17/21/25**: Pipeline per version runs build + unit tests on each push to `main`. Each badge reflects the last run status on the corresponding workflow.
- **Coverage**: Measured with JaCoCo and reported after the `coverage-tests.yml` workflow. Badge hosted on GitHub Pages updates on each push to `main`.
- **Integration API**: Live API tests run against the real DBC service. Requires credentials as CI secrets.
- **Selenium Integration**: Headless Chrome end-to-end test for reCAPTCHA v2 solving with Selenium WebDriver.
- **Maven Online**: Validates the published artifact is resolvable from Maven Central without source code.

## ⚖️ Responsible Use

See [Responsible Use Agreement](RESPONSIBLE_USE.md).

## ⚖️ License

MIT. See [LICENSE](LICENSE).