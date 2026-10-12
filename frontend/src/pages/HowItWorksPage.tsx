import React from 'react';
import { useLanguage } from '../components/LanguageContext';
import { ShieldCheck, BookOpen, AlertTriangle, FileText, CheckCircle2 } from 'lucide-react';

export const HowItWorksPage: React.FC = () => {
  const { t } = useLanguage();

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-12 space-y-12">
      <div className="text-center space-y-3">
        <h1 className="text-3xl font-extrabold text-[#1D2923]">{t('howItWorks')}</h1>
        <p className="text-sm text-[#66736B] max-w-xl mx-auto">
          Understand the technology, data sources, and scientific methodology behind the LabelLens intelligence platform.
        </p>
      </div>

      <div className="bg-white p-8 rounded-2xl border border-[#E1E8E2] shadow-xs space-y-8">
        <div className="flex items-start space-x-4">
          <div className="w-10 h-10 rounded-xl bg-[#EAF6EE] text-[#176B45] flex items-center justify-center shrink-0 font-bold">1</div>
          <div className="space-y-1">
            <h3 className="font-bold text-sm text-[#1D2923]">Label Image Capture & Local OCR</h3>
            <p className="text-xs text-[#66736B] leading-relaxed">
              When you upload a food packet photo, Tesseract.js processes the image directly in your browser or local system using WebAssembly. Your photos are not transmitted to or harvested by commercial cloud providers.
            </p>
          </div>
        </div>

        <div className="flex items-start space-x-4">
          <div className="w-10 h-10 rounded-xl bg-[#EAF6EE] text-[#176B45] flex items-center justify-center shrink-0 font-bold">2</div>
          <div className="space-y-1">
            <h3 className="font-bold text-sm text-[#1D2923]">Parenthesis-Safe Tokenization Engine</h3>
            <p className="text-xs text-[#66736B] leading-relaxed">
              Food labels frequently nest descriptions inside parentheses (e.g. <code>Emulsifier (INS 322(i))</code> or <code>Raising Agents (INS 500(ii), INS 503(ii))</code>). Our backend tokenization engine parses nested structures without splitting compound additives into corrupted fragments.
            </p>
          </div>
        </div>

        <div className="flex items-start space-x-4">
          <div className="w-10 h-10 rounded-xl bg-[#EAF6EE] text-[#176B45] flex items-center justify-center shrink-0 font-bold">3</div>
          <div className="space-y-1">
            <h3 className="font-bold text-sm text-[#1D2923]">Rule-Based Additive & Allergen Matching</h3>
            <p className="text-xs text-[#66736B] leading-relaxed">
              Tokens are matched against verified international INS/E-numbers (Codex Alimentarius) and regional FSSAI standards. Known allergens (milk, soy, peanuts, gluten) are flagged according to your personal dietary exclusions.
            </p>
          </div>
        </div>

        <div className="flex items-start space-x-4">
          <div className="w-10 h-10 rounded-xl bg-[#EAF6EE] text-[#176B45] flex items-center justify-center shrink-0 font-bold">4</div>
          <div className="space-y-1">
            <h3 className="font-bold text-sm text-[#1D2923]">Multilingual Plain-Language Explanations</h3>
            <p className="text-xs text-[#66736B] leading-relaxed">
              Explanations are presented in Tamil, Telugu, Hindi, Kannada, or English. Each card highlights the additive's primary functional purpose (preservative, stabilizer, emulsifier) and evidence-based safety guidance.
            </p>
          </div>
        </div>
      </div>

      <div className="p-6 bg-[#FAFAF6] rounded-2xl border border-[#E1E8E2] flex items-start space-x-4">
        <CheckCircle2 className="w-6 h-6 text-[#176B45] shrink-0 mt-0.5" />
        <div className="space-y-1">
          <h4 className="text-sm font-bold text-[#1D2923]">Scientific Neutrality Principle</h4>
          <p className="text-xs text-[#66736B] leading-relaxed">
            LabelLens does not generate arbitrary "health scores" or fear-mongering warnings. We distinguish between function, source, and established allergens to enable honest, informed consumer choice.
          </p>
        </div>
      </div>
    </div>
  );
};
