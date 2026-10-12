import React, { useState, useEffect } from 'react';
import { useAuth } from '../components/AuthContext';
import { useLanguage } from '../components/LanguageContext';
import { Link } from 'react-router-dom';
import { User, ShieldAlert, History, FileText, Check } from 'lucide-react';

export const DashboardPage: React.FC = () => {
  const { user, token } = useAuth();
  const { t } = useLanguage();

  const [allergens, setAllergens] = useState<string[]>(['Milk', 'Peanut', 'Soy', 'Wheat']);
  const [history, setHistory] = useState<any[]>([]);
  const [saveStatus, setSaveStatus] = useState<string | null>(null);

  useEffect(() => {
    if (token) {
      loadHistory();
      loadPreferences();
    }
  }, [token]);

  const loadPreferences = async () => {
    try {
      const res = await fetch('/api/v1/preferences', {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      if (res.ok) {
        const data = await res.json();
        if (data && data.length > 0) {
          setAllergens(data.map((d: any) => d.preferenceValue));
        }
      }
    } catch (e) {
      console.error(e);
    }
  };

  const loadHistory = async () => {
    try {
      const res = await fetch('/api/v1/analyses/history', {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      if (res.ok) {
        const data = await res.json();
        setHistory(data);
      }
    } catch (e) {
      console.error(e);
    }
  };

  const handleSavePreferences = async () => {
    try {
      const payload = allergens.map(a => ({
        preferenceType: 'ALLERGEN',
        preferenceValue: a
      }));

      const res = await fetch('/api/v1/preferences', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(payload)
      });

      if (res.ok) {
        setSaveStatus('Preferences saved successfully!');
        setTimeout(() => setSaveStatus(null), 3000);
      }
    } catch (e) {
      console.error(e);
    }
  };

  const toggleAllergen = (item: string) => {
    setAllergens(prev =>
      prev.includes(item) ? prev.filter(x => x !== item) : [...prev, item]
    );
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      <div className="bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div className="flex items-center space-x-4">
          <div className="w-12 h-12 bg-[#EAF6EE] text-[#176B45] rounded-xl flex items-center justify-center font-bold text-lg">
            {user?.displayName ? user.displayName.charAt(0).toUpperCase() : 'U'}
          </div>
          <div>
            <h1 className="text-xl font-bold text-[#1D2923]">{user?.displayName}</h1>
            <p className="text-xs text-[#66736B]">{user?.email}</p>
          </div>
        </div>

        <Link
          to="/analyze"
          className="px-4 py-2 bg-[#176B45] text-white rounded-xl text-xs font-semibold hover:bg-[#104B33] transition-colors inline-block text-center"
        >
          {t('analyzeLabel')}
        </Link>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
        {/* Allergen Management Settings */}
        <div className="md:col-span-1 bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs space-y-4">
          <div className="flex items-center space-x-2 text-[#176B45]">
            <ShieldAlert className="w-5 h-5" />
            <h2 className="font-bold text-sm text-[#1D2923]">Personal Allergen Alerts</h2>
          </div>
          <p className="text-xs text-[#66736B]">
            Configure ingredients and food groups you wish to be flagged automatically during scans.
          </p>

          <div className="flex flex-wrap gap-2 pt-2">
            {['Milk', 'Peanut', 'Soy', 'Wheat', 'Egg', 'Tree nuts', 'Gluten', 'Fish', 'Sesame'].map(item => (
              <button
                key={item}
                type="button"
                onClick={() => toggleAllergen(item)}
                className={`px-3 py-1.5 rounded-full text-xs font-semibold border transition-colors ${
                  allergens.includes(item)
                    ? 'bg-[#176B45] text-white border-[#176B45]'
                    : 'bg-[#FAFAF6] text-[#66736B] border-[#E1E8E2] hover:border-[#35A66F]'
                }`}
              >
                {allergens.includes(item) ? '✓ ' : '+ '}
                {item}
              </button>
            ))}
          </div>

          <button
            onClick={handleSavePreferences}
            className="w-full mt-4 py-2 bg-[#176B45] hover:bg-[#104B33] text-white text-xs font-semibold rounded-xl shadow-xs transition-colors cursor-pointer"
          >
            Save Preferences
          </button>
          {saveStatus && (
            <p className="text-center text-xs text-[#35A66F] font-semibold">{saveStatus}</p>
          )}
        </div>

        {/* Scan History */}
        <div className="md:col-span-2 bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs space-y-4">
          <div className="flex items-center space-x-2 text-[#176B45]">
            <History className="w-5 h-5" />
            <h2 className="font-bold text-sm text-[#1D2923]">Label Scan History</h2>
          </div>

          {history.length === 0 ? (
            <div className="text-center py-12 text-xs text-[#66736B] space-y-3">
              <FileText className="w-8 h-8 text-[#8C9891] mx-auto" />
              <p>You haven't analyzed any labels while signed in yet.</p>
              <Link to="/analyze" className="text-[#176B45] font-semibold hover:underline block">
                Analyze your first food label now →
              </Link>
            </div>
          ) : (
            <div className="divide-y divide-[#E1E8E2]/60">
              {history.map(item => (
                <div key={item.id} className="py-3 flex items-center justify-between">
                  <div className="space-y-0.5">
                    <h3 className="font-semibold text-xs text-[#1D2923]">{item.productName || 'Analyzed Food Label'}</h3>
                    <p className="text-[11px] text-[#8C9891]">
                      {item.inputType} • {item.identifiedIngredients} identified • {new Date(item.createdAt).toLocaleDateString()}
                    </p>
                  </div>
                  {item.allergenAlertsCount > 0 && (
                    <span className="px-2 py-0.5 bg-[#FFF8E6] text-[#D99216] font-semibold text-[10px] rounded">
                      {item.allergenAlertsCount} Alert{item.allergenAlertsCount > 1 ? 's' : ''}
                    </span>
                  )}
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
