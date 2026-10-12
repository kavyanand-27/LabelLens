import React from 'react';
import { Link } from 'react-router-dom';
import { useLanguage } from './LanguageContext';
import { ShieldCheck, Heart } from 'lucide-react';

export const Footer: React.FC = () => {
  const { t } = useLanguage();

  return (
    <footer className="bg-white border-t border-[#E1E8E2] mt-20 pt-12 pb-8 text-[#66736B] text-xs">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8 mb-8">
          <div className="space-y-3">
            <div className="flex items-center space-x-2 text-[#1D2923] font-bold text-base">
              <ShieldCheck className="w-5 h-5 text-[#176B45]" />
              <span>{t('appName')}</span>
            </div>
            <p className="leading-relaxed text-xs">
              {t('tagline')}
            </p>
            <p className="text-[11px] text-[#8C9891]">
              Zero-cost open-source intelligence for consumer food transparency.
            </p>
          </div>

          <div>
            <h4 className="font-semibold text-[#1D2923] mb-3 uppercase tracking-wider text-[11px]">Platform</h4>
            <ul className="space-y-2">
              <li><Link to="/analyze" className="hover:text-[#176B45]">{t('analyzeLabel')}</Link></li>
              <li><Link to="/ingredient-library" className="hover:text-[#176B45]">{t('ingredientLibrary')}</Link></li>
              <li><Link to="/compare" className="hover:text-[#176B45]">{t('compareProducts')}</Link></li>
              <li><Link to="/how-it-works" className="hover:text-[#176B45]">{t('howItWorks')}</Link></li>
            </ul>
          </div>

          <div>
            <h4 className="font-semibold text-[#1D2923] mb-3 uppercase tracking-wider text-[11px]">Trust & Policy</h4>
            <ul className="space-y-2">
              <li><Link to="/methodology" className="hover:text-[#176B45]">Scientific Methodology</Link></li>
              <li><Link to="/privacy" className="hover:text-[#176B45]">Privacy & Data Policy</Link></li>
              <li><Link to="/terms" className="hover:text-[#176B45]">Terms of Use</Link></li>
              <li><Link to="/about" className="hover:text-[#176B45]">About LabelLens</Link></li>
            </ul>
          </div>

          <div>
            <h4 className="font-semibold text-[#1D2923] mb-3 uppercase tracking-wider text-[11px]">Scientific Reference</h4>
            <p className="text-[11px] leading-relaxed text-[#66736B]">
              Taxonomies and safety evaluations based on Codex Alimentarius (CXS 192-1995), FSSAI food additive standards, and FAO/WHO JECFA databases.
            </p>
          </div>
        </div>

        <div className="border-t border-[#E1E8E2] pt-6 flex flex-col sm:flex-row items-center justify-between text-[11px] text-[#8C9891]">
          <p>© {new Date().getFullYear()} LabelLens — Multilingual Food Label Intelligence. All rights reserved.</p>
          <div className="flex items-center space-x-1 mt-2 sm:mt-0">
            <span>Built with scientific integrity & consumer empowerment</span>
            <Heart className="w-3.5 h-3.5 text-[#C43E3E] inline fill-current" />
          </div>
        </div>
      </div>
    </footer>
  );
};
