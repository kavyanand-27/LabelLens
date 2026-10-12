import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../components/AuthContext';
import { useLanguage } from '../components/LanguageContext';
import { ShieldCheck, AlertCircle } from 'lucide-react';

export const RegisterPage: React.FC = () => {
  const { language, t } = useLanguage();
  const { login } = useAuth();
  const navigate = useNavigate();

  const [displayName, setDisplayName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMsg, setErrorMsg] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (password.length < 6) {
      setErrorMsg("Password must contain at least 6 characters.");
      return;
    }

    setLoading(true);
    setErrorMsg(null);

    try {
      const res = await fetch('/api/v1/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ displayName, email, password, preferredLanguage: language })
      });

      const data = await res.json();
      if (!res.ok) {
        throw new Error(data.error || 'Registration failed');
      }

      login(data.token, {
        email: data.email,
        displayName: data.displayName,
        role: data.role,
        preferredLanguage: data.preferredLanguage
      });

      navigate('/dashboard');
    } catch (err: any) {
      setErrorMsg(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto px-4 py-16">
      <div className="bg-white p-8 rounded-2xl border border-[#E1E8E2] shadow-xs space-y-6">
        <div className="text-center space-y-2">
          <div className="w-12 h-12 bg-[#EAF6EE] text-[#176B45] rounded-xl flex items-center justify-center mx-auto">
            <ShieldCheck className="w-6 h-6" />
          </div>
          <h2 className="text-2xl font-bold text-[#1D2923]">{t('register')}</h2>
          <p className="text-xs text-[#66736B]">Join LabelLens to personalize allergen warnings and save label reports</p>
        </div>

        {errorMsg && (
          <div className="p-3 bg-[#FDF2F2] border border-[#C43E3E]/20 text-[#C43E3E] rounded-xl text-xs flex items-center space-x-2">
            <AlertCircle className="w-4 h-4 shrink-0" />
            <span>{errorMsg}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-xs font-semibold text-[#1D2923] mb-1">Full Name</label>
            <input
              type="text"
              required
              value={displayName}
              onChange={e => setDisplayName(e.target.value)}
              placeholder="e.g. Ramesh Kumar"
              className="w-full px-3 py-2 text-xs border border-[#E1E8E2] rounded-lg bg-[#FAFAF6] focus:bg-white focus:outline-none focus:border-[#176B45]"
            />
          </div>

          <div>
            <label className="block text-xs font-semibold text-[#1D2923] mb-1">Email Address</label>
            <input
              type="email"
              required
              value={email}
              onChange={e => setEmail(e.target.value)}
              placeholder="user@example.com"
              className="w-full px-3 py-2 text-xs border border-[#E1E8E2] rounded-lg bg-[#FAFAF6] focus:bg-white focus:outline-none focus:border-[#176B45]"
            />
          </div>

          <div>
            <label className="block text-xs font-semibold text-[#1D2923] mb-1">Password (min 6 chars)</label>
            <input
              type="password"
              required
              value={password}
              onChange={e => setPassword(e.target.value)}
              placeholder="••••••••"
              className="w-full px-3 py-2 text-xs border border-[#E1E8E2] rounded-lg bg-[#FAFAF6] focus:bg-white focus:outline-none focus:border-[#176B45]"
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full py-2.5 bg-[#176B45] hover:bg-[#104B33] disabled:bg-gray-400 text-white rounded-xl text-xs font-semibold shadow-xs transition-colors cursor-pointer"
          >
            {loading ? 'Creating Account...' : t('register')}
          </button>
        </form>

        <p className="text-center text-xs text-[#66736B]">
          Already have an account?{' '}
          <Link to="/login" className="font-semibold text-[#176B45] hover:underline">
            {t('signIn')}
          </Link>
        </p>
      </div>
    </div>
  );
};
