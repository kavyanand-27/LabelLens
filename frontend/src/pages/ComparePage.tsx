import React, { useState, useEffect } from 'react';
import { useLanguage } from '../components/LanguageContext';
import { api, Product } from '../services/api';
import { Scale, Check, AlertTriangle, ArrowRight } from 'lucide-react';

export const ComparePage: React.FC = () => {
  const { t } = useLanguage();
  const [products, setProducts] = useState<Product[]>([]);
  const [selectedP1, setSelectedP1] = useState<number | null>(null);
  const [selectedP2, setSelectedP2] = useState<number | null>(null);
  const [comparisonData, setComparisonData] = useState<any | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadProducts();
  }, []);

  const loadProducts = async () => {
    try {
      const data = await api.getProducts();
      setProducts(data);
      if (data.length >= 2) {
        setSelectedP1(data[0].id);
        setSelectedP2(data[1].id);
      }
    } catch (e) {
      console.error(e);
    }
  };

  const handleCompare = async () => {
    if (!selectedP1 || !selectedP2 || selectedP1 === selectedP2) {
      alert("Please select two distinct products to compare.");
      return;
    }
    setLoading(true);
    try {
      const res = await api.compareProducts([selectedP1, selectedP2]);
      setComparisonData(res);
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (selectedP1 && selectedP2 && selectedP1 !== selectedP2) {
      handleCompare();
    }
  }, [selectedP1, selectedP2]);

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      <div className="space-y-2">
        <h1 className="text-3xl font-extrabold text-[#1D2923] tracking-tight">{t('compareProducts')}</h1>
        <p className="text-sm text-[#66736B]">
          Evaluate food labels side-by-side to review shared ingredients, distinct additives, and nutritional facts.
        </p>
      </div>

      {/* Selectors */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6 bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs">
        <div>
          <label className="block text-xs font-semibold text-[#1D2923] mb-2">Select Product A:</label>
          <select
            value={selectedP1 || ''}
            onChange={e => setSelectedP1(Number(e.target.value))}
            className="w-full p-2.5 text-xs border border-[#E1E8E2] rounded-xl bg-[#FAFAF6] focus:bg-white focus:outline-none focus:border-[#176B45] font-medium"
          >
            {products.map(p => (
              <option key={p.id} value={p.id}>
                {p.productName} ({p.brand})
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-xs font-semibold text-[#1D2923] mb-2">Select Product B:</label>
          <select
            value={selectedP2 || ''}
            onChange={e => setSelectedP2(Number(e.target.value))}
            className="w-full p-2.5 text-xs border border-[#E1E8E2] rounded-xl bg-[#FAFAF6] focus:bg-white focus:outline-none focus:border-[#176B45] font-medium"
          >
            {products.map(p => (
              <option key={p.id} value={p.id}>
                {p.productName} ({p.brand})
              </option>
            ))}
          </select>
        </div>
      </div>

      {/* Comparison Results */}
      {comparisonData && (
        <div className="space-y-6 animate-fadeIn">
          {/* Side by side product header */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="bg-white p-5 rounded-2xl border border-[#E1E8E2] shadow-xs space-y-2">
              <span className="text-[11px] font-bold text-[#176B45] uppercase tracking-wider">Product 1</span>
              <h3 className="font-bold text-base text-[#1D2923]">{comparisonData.product1.productName}</h3>
              <p className="text-xs text-[#66736B]">Brand: {comparisonData.product1.brand} • Size: {comparisonData.product1.packageSize}</p>
            </div>
            <div className="bg-white p-5 rounded-2xl border border-[#E1E8E2] shadow-xs space-y-2">
              <span className="text-[11px] font-bold text-[#35A66F] uppercase tracking-wider">Product 2</span>
              <h3 className="font-bold text-base text-[#1D2923]">{comparisonData.product2.productName}</h3>
              <p className="text-xs text-[#66736B]">Brand: {comparisonData.product2.brand} • Size: {comparisonData.product2.packageSize}</p>
            </div>
          </div>

          {/* Shared vs Unique Breakdown */}
          <div className="bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs space-y-6">
            <h3 className="font-bold text-sm text-[#1D2923]">Ingredient Similarity Breakdown</h3>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-xs">
              <div className="p-4 bg-[#EAF6EE] rounded-xl border border-[#35A66F]/20 space-y-2">
                <span className="font-bold text-[#176B45] block">
                  Shared Ingredients ({comparisonData.sharedIngredients?.length || 0})
                </span>
                <ul className="space-y-1 text-[#1D2923]">
                  {comparisonData.sharedIngredients?.map((item: string, i: number) => (
                    <li key={i}>• {item}</li>
                  ))}
                </ul>
              </div>

              <div className="p-4 bg-[#FAFAF6] rounded-xl border border-[#E1E8E2] space-y-2">
                <span className="font-bold text-[#1D2923] block">
                  Only in {comparisonData.product1.productName} ({comparisonData.product1UniqueIngredients?.length || 0})
                </span>
                <ul className="space-y-1 text-[#66736B]">
                  {comparisonData.product1UniqueIngredients?.map((item: string, i: number) => (
                    <li key={i}>• {item}</li>
                  ))}
                </ul>
              </div>

              <div className="p-4 bg-[#FAFAF6] rounded-xl border border-[#E1E8E2] space-y-2">
                <span className="font-bold text-[#1D2923] block">
                  Only in {comparisonData.product2.productName} ({comparisonData.product2UniqueIngredients?.length || 0})
                </span>
                <ul className="space-y-1 text-[#66736B]">
                  {comparisonData.product2UniqueIngredients?.map((item: string, i: number) => (
                    <li key={i}>• {item}</li>
                  ))}
                </ul>
              </div>
            </div>
          </div>

          {/* Nutritional Comparison Table */}
          <div className="bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs space-y-4">
            <h3 className="font-bold text-sm text-[#1D2923]">Nutritional Profile Comparison (per 100g / 100ml basis)</h3>
            <div className="overflow-x-auto">
              <table className="w-full text-xs text-left">
                <thead>
                  <tr className="border-b border-[#E1E8E2] text-[#66736B]">
                    <th className="py-2.5 font-semibold">Nutrient</th>
                    <th className="py-2.5 font-semibold">{comparisonData.product1.productName}</th>
                    <th className="py-2.5 font-semibold">{comparisonData.product2.productName}</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-[#E1E8E2]/50 text-[#1D2923]">
                  <tr>
                    <td className="py-2 font-medium">Energy</td>
                    <td className="py-2">{comparisonData.product1.energyKcal ?? 'N/A'} kcal</td>
                    <td className="py-2">{comparisonData.product2.energyKcal ?? 'N/A'} kcal</td>
                  </tr>
                  <tr>
                    <td className="py-2 font-medium">Protein</td>
                    <td className="py-2">{comparisonData.product1.proteinG ?? 'N/A'} g</td>
                    <td className="py-2">{comparisonData.product2.proteinG ?? 'N/A'} g</td>
                  </tr>
                  <tr>
                    <td className="py-2 font-medium">Total Sugars</td>
                    <td className="py-2">{comparisonData.product1.totalSugarsG ?? 'N/A'} g</td>
                    <td className="py-2">{comparisonData.product2.totalSugarsG ?? 'N/A'} g</td>
                  </tr>
                  <tr>
                    <td className="py-2 font-medium">Added Sugars</td>
                    <td className="py-2">{comparisonData.product1.addedSugarsG ?? 'N/A'} g</td>
                    <td className="py-2">{comparisonData.product2.addedSugarsG ?? 'N/A'} g</td>
                  </tr>
                  <tr>
                    <td className="py-2 font-medium">Total Fat</td>
                    <td className="py-2">{comparisonData.product1.totalFatG ?? 'N/A'} g</td>
                    <td className="py-2">{comparisonData.product2.totalFatG ?? 'N/A'} g</td>
                  </tr>
                  <tr>
                    <td className="py-2 font-medium">Sodium</td>
                    <td className="py-2">{comparisonData.product1.sodiumMg ?? 'N/A'} mg</td>
                    <td className="py-2">{comparisonData.product2.sodiumMg ?? 'N/A'} mg</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <p className="text-[10px] text-[#8C9891] pt-2">
              * Note: "N/A" indicates that the respective value was omitted or not reported on the packaging label.
            </p>
          </div>
        </div>
      )}
    </div>
  );
};
