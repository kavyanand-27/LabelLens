import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useLanguage } from './LanguageContext';
import { useAuth } from './AuthContext';
import { SupportedLanguage } from '../locales/translations';
import { ShieldCheck, Search, FileText, Scale, Globe, User, LogOut } from 'lucide-react';

export const Header: React.FC = () => {
  const { language, setLanguage, t } = useLanguage();
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLangChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setLanguage(e.target.value as SupportedLanguage);
  };

  return (
    <header className="sticky top-0 z-50 bg-white/95 backdrop-blur-md border-b border-[#E1E8E2] shadow-xs">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-18 flex items-center justify-between">
        <Link to="/" className="flex items-center space-x-3 group">
          <div className="w-10 h-10 rounded-xl bg-[#176B45] text-white flex items-center justify-center shadow-sm group-hover:scale-105 transition-transform">
            <ShieldCheck className="w-6 h-6" />
          </div>
          <div>
            <span className="text-xl font-bold tracking-tight text-[#1D2923]">{t('appName')}</span>
            <span className="block text-[11px] text-[#66736B] font-medium leading-tight">Food Intelligence</span>
          </div>
        </Link>

        {/* Navigation links */}
        <nav className="hidden md:flex items-center space-x-6 text-sm font-medium text-[#1D2923]">
          <Link to="/analyze" className="hover:text-[#176B45] flex items-center space-x-1.5 transition-colors">
            <FileText className="w-4 h-4 text-[#35A66F]" />
            <span>{t('analyzeLabel')}</span>
          </Link>
          <Link to="/ingredient-library" className="hover:text-[#176B45] flex items-center space-x-1.5 transition-colors">
            <Search className="w-4 h-4 text-[#35A66F]" />
            <span>{t('ingredientLibrary')}</span>
          </Link>
          <Link to="/compare" className="hover:text-[#176B45] flex items-center space-x-1.5 transition-colors">
            <Scale className="w-4 h-4 text-[#35A66F]" />
            <span>{t('compareProducts')}</span>
          </Link>
          <Link to="/how-it-works" className="hover:text-[#176B45] transition-colors">
            {t('howItWorks')}
          </Link>
        </nav>

        {/* Language selector & User action */}
        <div className="flex items-center space-x-3">
          <div className="flex items-center bg-[#FAFAF6] border border-[#E1E8E2] rounded-lg px-2.5 py-1.5 text-xs text-[#1D2923] shadow-xs">
            <Globe className="w-3.5 h-3.5 mr-1.5 text-[#176B45]" />
            <select
              value={language}
              onChange={handleLangChange}
              className="bg-transparent border-none outline-none font-medium cursor-pointer"
            >
              <option value="en">English</option>
              <option value="ta">தமிழ் (Tamil)</option>
              <option value="te">తెలుగు (Telugu)</option>
              <option value="hi">हिन्दी (Hindi)</option>
              <option value="kn">ಕನ್ನಡ (Kannada)</option>
            </select>
          </div>

          {user ? (
            <div className="flex items-center space-x-2">
              <Link
                to="/dashboard"
                className="flex items-center space-x-1 px-3 py-1.5 bg-[#EAF6EE] text-[#176B45] rounded-lg text-xs font-semibold hover:bg-[#d8eedf] transition-colors"
              >
                <User className="w-3.5 h-3.5" />
                <span className="hidden sm:inline">{user.displayName}</span>
              </Link>
              <button
                onClick={() => { logout(); navigate('/'); }}
                className="p-1.5 text-[#66736B] hover:text-[#C43E3E] rounded-lg border border-[#E1E8E2] transition-colors"
                title={t('signOut')}
              >
                <LogOut className="w-4 h-4" />
              </button>
            </div>
          ) : (
            <div className="flex items-center space-x-2">
              <Link
                to="/login"
                className="px-3 py-1.5 text-xs font-semibold text-[#176B45] hover:text-[#104B33] transition-colors"
              >
                {t('signIn')}
              </Link>
              <Link
                to="/register"
                className="px-3.5 py-1.5 bg-[#176B45] hover:bg-[#104B33] text-white rounded-lg text-xs font-semibold shadow-xs transition-colors"
              >
                {t('register')}
              </Link>
            </div>
          )}
        </div>
      </div>
    </header>
  );
};
