import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../components/AuthContext';
import { useLanguage } from '../components/LanguageContext';
import { ShieldCheck, AlertCircle } from 'lucide-react';

export const LoginPage: React.FC = () => {
  const { t } = useLanguage();
  const { login } = useAuth();
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMsg, setErrorMsg] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setErrorMsg(null);

    try {
      const res = await fetch('/api/v1/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });

      const data = await res.json();
      if (!res.ok) {
        throw new Error(data.error || 'Authentication failed');
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
          <h2 className="text-2xl font-bold text-[#1D2923]">{t('signIn')}</h2>
          <p className="text-xs text-[#66736B]">Access your saved scan history and allergen preferences</p>
        </div>

        {errorMsg && (
          <div className="p-3 bg-[#FDF2F2] border border-[#C43E3E]/20 text-[#C43E3E] rounded-xl text-xs flex items-center space-x-2">
            <AlertCircle className="w-4 h-4 shrink-0" />
            <span>{errorMsg}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
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
            <label className="block text-xs font-semibold text-[#1D2923] mb-1">Password</label>
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
            {loading ? 'Authenticating...' : t('signIn')}
          </button>
        </form>

        <p className="text-center text-xs text-[#66736B]">
          Don't have an account?{' '}
          <Link to="/register" className="font-semibold text-[#176B45] hover:underline">
            Register now
          </Link>
        </p>
      </div>
    </div>
  );
};
