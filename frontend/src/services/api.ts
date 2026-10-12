const API_BASE = '/api/v1';

export interface Ingredient {
  id: number;
  canonicalName: string;
  slug: string;
  description: string;
  additiveCode: string | null;
  category: string;
  commonAllergen: boolean;
  allergenType: string | null;
  purposeEn: string;
  purposeTa: string;
  purposeTe: string;
  purposeHi: string;
  purposeKn: string;
  explanationEn: string;
  explanationTa: string;
  explanationTe: string;
  explanationHi: string;
  explanationKn: string;
  precautionsEn: string;
  precautionsTa: string;
  precautionsTe: string;
  precautionsHi: string;
  precautionsKn: string;
  commonFoods: string;
  sources: string;
}

export interface AnalysisItem {
  originalTerm: string;
  ingredientId?: number;
  canonicalName?: string;
  additiveCode?: string;
  category?: string;
  matchMethod: string;
  status: string;
  allergen: boolean;
  allergenType?: string;
  localizedExplanation?: string;
  localizedPurpose?: string;
  localizedPrecautions?: string;
  commonFoods?: string;
  sources?: string;
}

export interface AnalysisAlert {
  alertType: string;
  matchedPreference: string;
  originalTerm: string;
  explanation: string;
}

export interface AnalysisResult {
  analysisId?: number;
  productName: string;
  brand?: string;
  inputType: string;
  totalIngredients: number;
  identifiedIngredients: number;
  allergenAlertsCount: number;
  items: AnalysisItem[];
  alerts: AnalysisAlert[];
  categoryBreakdown: Record<string, string[]>;
}

export interface Product {
  id: number;
  productName: string;
  brand: string;
  category: string;
  barcode: string;
  packageSize: string;
  rawIngredients: string;
  energyKcal?: number;
  proteinG?: number;
  carbohydrateG?: number;
  totalSugarsG?: number;
  addedSugarsG?: number;
  totalFatG?: number;
  saturatedFatG?: number;
  transFatG?: number;
  dietaryFiberG?: number;
  sodiumMg?: number;
  source?: string;
  verified: boolean;
}

export const api = {
  async getIngredients(category?: string, search?: string): Promise<Ingredient[]> {
    const params = new URLSearchParams();
    if (category) params.append('category', category);
    if (search) params.append('search', search);
    const res = await fetch(`${API_BASE}/ingredients?${params.toString()}`);
    if (!res.ok) throw new Error('Failed to fetch ingredients');
    return res.json();
  },

  async getCategories(): Promise<string[]> {
    const res = await fetch(`${API_BASE}/ingredients/categories`);
    if (!res.ok) throw new Error('Failed to fetch categories');
    return res.json();
  },

  async analyzeText(payload: {
    rawText: string;
    productName?: string;
    brand?: string;
    inputType?: string;
    lang: string;
    allergens?: string[];
  }, token?: string | null): Promise<AnalysisResult> {
    const headers: Record<string, string> = { 'Content-Type': 'application/json' };
    if (token) headers['Authorization'] = `Bearer ${token}`;

    const res = await fetch(`${API_BASE}/analyses/text`, {
      method: 'POST',
      headers,
      body: JSON.stringify(payload)
    });
    if (!res.ok) throw new Error('Failed to perform analysis');
    return res.json();
  },

  async getProducts(search?: string, category?: string): Promise<Product[]> {
    const params = new URLSearchParams();
    if (search) params.append('search', search);
    if (category) params.append('category', category);
    const res = await fetch(`${API_BASE}/products?${params.toString()}`);
    if (!res.ok) throw new Error('Failed to fetch products');
    return res.json();
  },

  async compareProducts(productIds: number[]): Promise<any> {
    const res = await fetch(`${API_BASE}/comparisons`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ productIds })
    });
    if (!res.ok) throw new Error('Failed to perform product comparison');
    return res.json();
  },

  async submitCorrection(payload: {
    targetType: string;
    termName: string;
    suggestedCorrection: string;
    submittedByEmail?: string;
  }): Promise<any> {
    const res = await fetch(`${API_BASE}/corrections`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!res.ok) throw new Error('Failed to submit correction');
    return res.json();
  }
};
