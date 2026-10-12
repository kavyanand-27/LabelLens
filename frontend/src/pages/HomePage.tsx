import React from 'react';
import { Link } from 'react-router-dom';
import { useLanguage } from '../components/LanguageContext';
import { 
  FileSearch, BookOpen, Scale, ShieldAlert, CheckCircle2, ArrowRight,
  Sparkles, Layers, Globe2
} from 'lucide-react';

export const HomePage: React.FC = () => {
  const { t } = useLanguage();

  return (
    <div className="space-y-20 pb-16">
      {/* Hero Section */}
      <section className="relative overflow-hidden pt-12 md:pt-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center space-y-8">
          <div className="inline-flex items-center space-x-2 bg-[#EAF6EE] text-[#176B45] px-4 py-1.5 rounded-full text-xs font-semibold tracking-wide border border-[#35A66F]/20">
            <Sparkles className="w-3.5 h-3.5" />
            <span>Zero-Cost Open-Source Food Intelligence Platform</span>
          </div>

          <h1 className="text-4xl sm:text-5xl lg:text-6xl font-extrabold text-[#1D2923] tracking-tight max-w-4xl mx-auto leading-tight">
            {t('heroTitle')}
          </h1>

          <p className="text-lg text-[#66736B] max-w-2xl mx-auto leading-relaxed">
            {t('heroSubtitle')}
          </p>

          <div className="flex flex-col sm:flex-row items-center justify-center gap-4 pt-4">
            <Link
              to="/analyze"
              className="w-full sm:w-auto px-8 py-3.5 bg-[#176B45] hover:bg-[#104B33] text-white rounded-xl font-semibold shadow-md hover:shadow-lg transition-all flex items-center justify-center space-x-2"
            >
              <span>{t('analyzeLabel')}</span>
              <ArrowRight className="w-4 h-4" />
            </Link>
            <Link
              to="/ingredient-library"
              className="w-full sm:w-auto px-8 py-3.5 bg-white hover:bg-[#FAFAF6] text-[#1D2923] border border-[#E1E8E2] rounded-xl font-semibold shadow-xs transition-all flex items-center justify-center space-x-2"
            >
              <span>{t('exploreIngredients')}</span>
            </Link>
          </div>

          {/* Quick Stats / Transparency Bar */}
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4 max-w-4xl mx-auto pt-10 border-t border-[#E1E8E2]/60">
            <div className="p-4 bg-white rounded-xl border border-[#E1E8E2] text-left">
              <span className="block text-2xl font-bold text-[#176B45]">5</span>
              <span className="text-xs text-[#66736B]">Regional Languages (EN, TA, TE, HI, KN)</span>
            </div>
            <div className="p-4 bg-white rounded-xl border border-[#E1E8E2] text-left">
              <span className="block text-2xl font-bold text-[#176B45]">100%</span>
              <span className="text-xs text-[#66736B]">Offline Local Rule Engine (No Paid APIs)</span>
            </div>
            <div className="p-4 bg-white rounded-xl border border-[#E1E8E2] text-left">
              <span className="block text-2xl font-bold text-[#176B45]">Codex/FSSAI</span>
              <span className="text-xs text-[#66736B]">Evidence-Based Regulatory Standards</span>
            </div>
            <div className="p-4 bg-white rounded-xl border border-[#E1E8E2] text-left">
              <span className="block text-2xl font-bold text-[#176B45]">Real-time</span>
              <span className="text-xs text-[#66736B]">Client & Server OCR Label Recognition</span>
            </div>
          </div>
        </div>
      </section>

      {/* 6 Core Feature Cards */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center max-w-2xl mx-auto mb-12">
          <h2 className="text-2xl sm:text-3xl font-bold text-[#1D2923]">What LabelLens Empowers You To Do</h2>
          <p className="text-sm text-[#66736B] mt-2">Every feature works out of the box with zero third-party subscriptions.</p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs hover:border-[#35A66F] transition-colors space-y-3">
            <div className="w-10 h-10 rounded-xl bg-[#EAF6EE] text-[#176B45] flex items-center justify-center">
              <FileSearch className="w-5 h-5" />
            </div>
            <h3 className="font-bold text-base text-[#1D2923]">Scan Food Labels</h3>
            <p className="text-xs text-[#66736B] leading-relaxed">
              Upload clear photographs of ingredient packets. Local OCR extracts text while you retain full edit and review control.
            </p>
          </div>

          <div className="bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs hover:border-[#35A66F] transition-colors space-y-3">
            <div className="w-10 h-10 rounded-xl bg-[#EAF6EE] text-[#176B45] flex items-center justify-center">
              <BookOpen className="w-5 h-5" />
            </div>
            <h3 className="font-bold text-base text-[#1D2923]">Understand Every Ingredient</h3>
            <p className="text-xs text-[#66736B] leading-relaxed">
              Demystify INS and E-numbers (e.g. INS 211, INS 322). Learn why each additive is added to your packaged snacks.
            </p>
          </div>

          <div className="bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs hover:border-[#35A66F] transition-colors space-y-3">
            <div className="w-10 h-10 rounded-xl bg-[#EAF6EE] text-[#176B45] flex items-center justify-center">
              <ShieldAlert className="w-5 h-5" />
            </div>
            <h3 className="font-bold text-base text-[#1D2923]">Check Personal Allergens</h3>
            <p className="text-xs text-[#66736B] leading-relaxed">
              Set preferences for Milk, Peanuts, Soy, Wheat, Gluten, or custom exclusions to highlight matching declared additives.
            </p>
          </div>

          <div className="bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs hover:border-[#35A66F] transition-colors space-y-3">
            <div className="w-10 h-10 rounded-xl bg-[#EAF6EE] text-[#176B45] flex items-center justify-center">
              <Scale className="w-5 h-5" />
            </div>
            <h3 className="font-bold text-base text-[#1D2923]">Compare Food Products</h3>
            <p className="text-xs text-[#66736B] leading-relaxed">
              Place two products side-by-side to review shared ingredients, differing preservatives, and recorded nutritional facts.
            </p>
          </div>

          <div className="bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs hover:border-[#35A66F] transition-colors space-y-3">
            <div className="w-10 h-10 rounded-xl bg-[#EAF6EE] text-[#176B45] flex items-center justify-center">
              <Globe2 className="w-5 h-5" />
            </div>
            <h3 className="font-bold text-base text-[#1D2923]">5 Regional Indian Languages</h3>
            <p className="text-xs text-[#66736B] leading-relaxed">
              Read verified explanations in Tamil, Telugu, Hindi, Kannada, and English with instant on-the-fly language switching.
            </p>
          </div>

          <div className="bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs hover:border-[#35A66F] transition-colors space-y-3">
            <div className="w-10 h-10 rounded-xl bg-[#EAF6EE] text-[#176B45] flex items-center justify-center">
              <Layers className="w-5 h-5" />
            </div>
            <h3 className="font-bold text-base text-[#1D2923]">Evidence & Transparency</h3>
            <p className="text-xs text-[#66736B] leading-relaxed">
              No fear-mongering or fake health scores. Pure scientific facts citing Codex Alimentarius, FSSAI, and JECFA evaluations.
            </p>
          </div>
        </div>
      </section>

      {/* How It Works - 4 Steps */}
      <section className="bg-white py-16 border-y border-[#E1E8E2]">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center max-w-2xl mx-auto mb-12">
            <h2 className="text-2xl sm:text-3xl font-bold text-[#1D2923]">How LabelLens Works</h2>
            <p className="text-sm text-[#66736B] mt-2">A completely verifiable four-step process for complete transparency</p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
            <div className="space-y-3">
              <span className="w-8 h-8 rounded-full bg-[#176B45] text-white flex items-center justify-center font-bold text-sm">1</span>
              <h4 className="font-semibold text-sm text-[#1D2923]">Upload or Type</h4>
              <p className="text-xs text-[#66736B]">Provide a clear label photo or copy the printed ingredient list directly into the workspace.</p>
            </div>
            <div className="space-y-3">
              <span className="w-8 h-8 rounded-full bg-[#176B45] text-white flex items-center justify-center font-bold text-sm">2</span>
              <h4 className="font-semibold text-sm text-[#1D2923]">Tokenize & Extract</h4>
              <p className="text-xs text-[#66736B]">Local OCR extracts text, followed by parenthesis-safe tokenization of individual food components.</p>
            </div>
            <div className="space-y-3">
              <span className="w-8 h-8 rounded-full bg-[#176B45] text-white flex items-center justify-center font-bold text-sm">3</span>
              <h4 className="font-semibold text-sm text-[#1D2923]">Rule-Based Matching</h4>
              <p className="text-xs text-[#66736B]">Identifies additives, INS codes, synonyms, and matches with your personal allergen settings.</p>
            </div>
            <div className="space-y-3">
              <span className="w-8 h-8 rounded-full bg-[#176B45] text-white flex items-center justify-center font-bold text-sm">4</span>
              <h4 className="font-semibold text-sm text-[#1D2923]">Review in Your Language</h4>
              <p className="text-xs text-[#66736B]">Inspect verified explanations in Tamil, Telugu, Hindi, Kannada, or English and export the report.</p>
            </div>
          </div>
        </div>
      </section>

      {/* Scientific Integrity Disclaimer */}
      <section className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="p-6 bg-[#FAFAF6] rounded-2xl border border-[#E1E8E2] flex items-start space-x-4">
          <CheckCircle2 className="w-6 h-6 text-[#176B45] shrink-0 mt-0.5" />
          <div className="space-y-1">
            <h4 className="text-sm font-bold text-[#1D2923]">{t('disclaimerTitle')}</h4>
            <p className="text-xs text-[#66736B] leading-relaxed">
              {t('disclaimerText')}
            </p>
          </div>
        </div>
      </section>
    </div>
  );
};
