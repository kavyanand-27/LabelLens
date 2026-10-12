# LabelLens — Multilingual Food Ingredient, Additive & Allergen Intelligence Platform

> **"Understand Every Ingredient. Choose With Confidence."**
> Food labels made simple — in the language you understand.

LabelLens is a full-stack, free-to-use food label intelligence platform. It operates with **zero mandatory paid APIs, zero subscriptions, zero cloud AI costs**, and features an entirely local rule-based additive classification engine, client-side WebAssembly OCR via Tesseract.js, persistent database storage, and reviewed multilingual explanations in 5 languages: **English, தமிழ் (Tamil), తెలుగు (Telugu), हिन्दी (Hindi), and ಕನ್ನಡ (Kannada)**.

---

## 🌟 Key Features

1. **Local Rule-Based Ingredient Intelligence Engine:**
   - Parenthesis-safe tokenization handling nested food label declarations (e.g. `Emulsifier (INS 322(i))`, `Raising Agents (INS 500(ii), INS 503(ii))`).
   - Additive code matching (`INS`, `E-numbers`) and synonym resolution.
   - Unknown ingredient handling: non-matching terms remain transparently unverified rather than falsely identified or hallucinatory.

2. **Multilingual Plain-Language Explanations:**
   - Support for **English (en)**, **Tamil (ta)**, **Telugu (te)**, **Hindi (hi)**, and **Kannada (kn)**.
   - Dynamic real-time interface and explanation language switching without losing route state.
   - Web Speech API integration for accessible audio readouts in regional Indian accents.

3. **Conservative Allergen & Dietary Alert System:**
   - Saved personal allergy preferences (Milk, Peanut, Soy, Wheat, Egg, Gluten, Tree nuts).
   - Prominent declared allergen alerts.
   - Explicit disclaimer that the absence of a detected allergen in extracted text does not guarantee the product is allergen-free.

4. **Product Database & Side-by-Side Comparison:**
   - Catalog of packaged products with nutrition facts and ingredients.
   - Side-by-side comparison engine calculating shared ingredients, unique additives, and nutritional variations per 100g/ml.

5. **Client-Side Local OCR (Zero-Cost & Private):**
   - In-browser label image scanning via `Tesseract.js` WebAssembly.
   - Label photo data never leaves the user's browser, eliminating external cloud OCR API fees.

6. **Authentication & User Dashboard:**
   - BCrypt password hashing, stateless JWT session tokens.
   - User dashboard storing personal allergen preferences and scan history.

7. **Scientific Neutrality & Regulatory Standards:**
   - Additive functions and safety precautions cite authoritative sources: **Codex Alimentarius (CXS 192-1995)** and **FSSAI Food Additive Standards**.
   - No arbitrary or misleading "health scores".

---

## 🏗️ Technology Stack

| Layer | Technology | License |
| :--- | :--- | :--- |
| **Frontend** | React 19, TypeScript, Vite, Tailwind CSS v4, Lucide React | MIT / ISC |
| **OCR Engine** | Tesseract.js (WebAssembly / Client-side OCR) | Apache 2.0 |
| **Backend API** | Java 21, Spring Boot 3.3.4, Spring Security, Spring Data JPA | Apache 2.0 |
| **Authentication** | BCrypt + JJWT (JSON Web Token) | Apache 2.0 |
| **Database** | Persistent file-backed H2 (Zero-setup local development) / PostgreSQL | Permissive / OSS |

---

## 🚀 Running the Project Locally

### Prerequisites
- **Java Development Kit (JDK 21 or later)**
- **Node.js (v18 or later) & npm**

### 1. Start the Spring Boot Backend
Open a terminal in the project root:
```powershell
cd c:\Users\HP\Documents\PROTO\backend
..\apache-maven-3.9.9\bin\mvn.cmd spring-boot:run
```
*The backend API will start on `http://localhost:8080`. Seed data containing additives, INS numbers, allergen tags, and products will automatically populate.*

### 2. Start the Vite React Frontend
Open another terminal:
```powershell
cd c:\Users\HP\Documents\PROTO\frontend
npm run dev
```
*The website will be accessible at `http://127.0.0.1:3000` (or `http://localhost:3000`).*

---

## 🧪 Automated Testing
Run the backend automated test suite:
```powershell
cd c:\Users\HP\Documents\PROTO\backend
..\apache-maven-3.9.9\bin\mvn.cmd test
```
*Tests verify parenthesis tokenization, additive code identification, unknown ingredient safety, and allergen matching.*

---

## 🔒 Security & Privacy Practices
- **Passwords:** Hashed with Spring Security `BCryptPasswordEncoder`.
- **CORS:** Restricted and handled via Spring Security filter chain.
- **Privacy:** OCR processing runs client-side inside the user's browser; user label images are not transmitted to third-party AI APIs.

---

## ⚖️ Scientific Disclaimer
LabelLens provides informational explanations based on published regulatory standards. The presence of food additives alone does not determine whether a product is suitable or safe for an individual with specific medical conditions. Always verify the original packaging and consult qualified healthcare professionals.
