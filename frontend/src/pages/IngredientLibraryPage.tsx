import React, { useState, useEffect } from 'react';
import { useLanguage } from '../components/LanguageContext';
import { api, Ingredient } from '../services/api';
import { Search, Filter, ShieldCheck, AlertCircle, Volume2, BookOpen } from 'lucide-react';

export const IngredientLibraryPage: React.FC = () => {
  const { language, t } = useLanguage();
  const [ingredients, setIngredients] = useState<Ingredient[]>([]);
  const [categories, setCategories] = useState<string[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string>('ALL');
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadCategories();
    loadIngredients();
  }, [selectedCategory]);

  const loadCategories = async () => {
    try {
      const cats = await api.getCategories();
      setCategories(cats);
    } catch (e) {
      console.error(e);
    }
  };

  const loadIngredients = async (term?: string) => {
    setLoading(true);
    try {
      const data = await api.getIngredients(selectedCategory, term !== undefined ? term : searchTerm);
      setIngredients(data);
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  const handleSearchSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    loadIngredients(searchTerm);
  };

  const getLocalizedExplanation = (ing: Ingredient) => {
    switch (language) {
      case 'ta': return ing.explanationTa || ing.explanationEn;
      case 'te': return ing.explanationTe || ing.explanationEn;
      case 'hi': return ing.explanationHi || ing.explanationEn;
      case 'kn': return ing.explanationKn || ing.explanationEn;
      default: return ing.explanationEn;
    }
  };

  const playSpeech = (text: string) => {
    if ('speechSynthesis' in window) {
      window.speechSynthesis.cancel();
      const u = new SpeechSynthesisUtterance(text);
      u.lang = language === 'ta' ? 'ta-IN' : language === 'te' ? 'te-IN' : language === 'hi' ? 'hi-IN' : language === 'kn' ? 'kn-IN' : 'en-US';
      window.speechSynthesis.speak(u);
    }
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      <div className="space-y-2">
        <h1 className="text-3xl font-extrabold text-[#1D2923] tracking-tight">{t('ingredientLibrary')}</h1>
        <p className="text-sm text-[#66736B]">
          Explore additives, preservatives, emulsifiers, sweeteners, and colours verified against Codex Alimentarius & FSSAI standards.
        </p>
      </div>

      {/* Search and Filters */}
      <div className="bg-white p-4 rounded-2xl border border-[#E1E8E2] shadow-xs flex flex-col md:flex-row gap-4 items-center justify-between">
        <form onSubmit={handleSearchSubmit} className="relative w-full md:w-96">
          <Search className="w-4 h-4 text-[#8C9891] absolute left-3 top-3" />
          <input
            type="text"
            value={searchTerm}
            onChange={e => setSearchTerm(e.target.value)}
            placeholder={t('searchPlaceholder')}
            className="w-full pl-9 pr-3 py-2 text-xs border border-[#E1E8E2] rounded-xl bg-[#FAFAF6] focus:bg-white focus:outline-none focus:border-[#176B45]"
          />
        </form>

        <div className="flex items-center space-x-2 w-full md:w-auto overflow-x-auto pb-2 md:pb-0">
          <Filter className="w-4 h-4 text-[#176B45] shrink-0" />
          <button
            onClick={() => setSelectedCategory('ALL')}
            className={`px-3 py-1.5 rounded-lg text-xs font-semibold whitespace-nowrap transition-colors ${
              selectedCategory === 'ALL'
                ? 'bg-[#176B45] text-white'
                : 'bg-[#FAFAF6] text-[#66736B] hover:bg-gray-100'
            }`}
          >
            All Categories
          </button>
          {categories.map(cat => (
            <button
              key={cat}
              onClick={() => setSelectedCategory(cat)}
              className={`px-3 py-1.5 rounded-lg text-xs font-semibold whitespace-nowrap transition-colors ${
                selectedCategory === cat
                  ? 'bg-[#176B45] text-white'
                  : 'bg-[#FAFAF6] text-[#66736B] hover:bg-gray-100'
              }`}
            >
              {cat}
            </button>
          ))}
        </div>
      </div>

      {/* Grid of Ingredients */}
      {loading ? (
        <div className="text-center py-16 text-xs text-[#66736B]">Loading ingredient intelligence database...</div>
      ) : ingredients.length === 0 ? (
        <div className="text-center py-16 bg-white rounded-2xl border border-[#E1E8E2] text-xs text-[#66736B]">
          No matching ingredients found for this query.
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {ingredients.map(ing => (
            <div
              key={ing.id}
              className="bg-white p-5 rounded-2xl border border-[#E1E8E2] hover:border-[#35A66F] shadow-xs flex flex-col justify-between space-y-4 transition-all"
            >
              <div className="space-y-3">
                <div className="flex items-start justify-between">
                  <div>
                    <h3 className="font-bold text-sm text-[#1D2923]">{ing.canonicalName}</h3>
                    {ing.additiveCode && (
                      <span className="inline-block mt-1 px-2 py-0.5 bg-[#EAF6EE] text-[#176B45] font-mono text-[11px] font-bold rounded">
                        {ing.additiveCode}
                      </span>
                    )}
                  </div>
                  <span className="px-2 py-0.5 bg-gray-100 text-[#66736B] text-[10px] font-semibold rounded uppercase">
                    {ing.category}
                  </span>
                </div>

                <p className="text-xs text-[#1D2923] leading-relaxed">
                  {getLocalizedExplanation(ing)}
                </p>

                {ing.commonAllergen && (
                  <div className="p-2 bg-[#FFF8E6] text-[#D99216] rounded-lg text-[11px] flex items-center space-x-1 font-semibold">
                    <AlertCircle className="w-3.5 h-3.5 shrink-0" />
                    <span>Contains Allergen: {ing.allergenType}</span>
                  </div>
                )}
              </div>

              <div className="pt-3 border-t border-[#E1E8E2] flex items-center justify-between text-[11px] text-[#8C9891]">
                <span>{ing.sources || 'Codex Alimentarius'}</span>
                <button
                  onClick={() => playSpeech(`${ing.canonicalName}. ${getLocalizedExplanation(ing)}`)}
                  className="p-1 text-[#176B45] hover:bg-[#EAF6EE] rounded transition-colors"
                  title="Listen"
                >
                  <Volume2 className="w-4 h-4" />
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};
