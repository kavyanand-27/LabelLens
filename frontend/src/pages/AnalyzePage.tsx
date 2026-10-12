import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLanguage } from '../components/LanguageContext';
import { useAuth } from '../components/AuthContext';
import { api, AnalysisResult } from '../services/api';
import { createWorker } from 'tesseract.js';
import { 
  UploadCloud, FileText, CheckCircle, AlertTriangle, ShieldCheck, 
  HelpCircle, RefreshCw, Volume2, ArrowRight, XCircle
} from 'lucide-react';

export const AnalyzePage: React.FC = () => {
  const { language, t } = useLanguage();
  const { token } = useAuth();
  const navigate = useNavigate();

  const [activeTab, setActiveTab] = useState<'manual' | 'upload'>('manual');
  const [productName, setProductName] = useState('');
  const [brand, setBrand] = useState('');
  const [rawText, setRawText] = useState('');
  
  // Allergen preference toggles
  const [selectedAllergens, setSelectedAllergens] = useState<string[]>(['Milk', 'Peanut', 'Soy', 'Wheat']);
  
  // OCR states
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [imagePreview, setImagePreview] = useState<string | null>(null);
  const [isOcrProcessing, setIsOcrProcessing] = useState(false);
  const [ocrProgress, setOcrProgress] = useState<string>('');

  // Analysis submission states
  const [isAnalyzing, setIsAnalyzing] = useState(false);
  const [result, setResult] = useState<AnalysisResult | null>(null);
  const [errorMsg, setErrorMsg] = useState<string | null>(null);

  const toggleAllergen = (item: string) => {
    setSelectedAllergens(prev => 
      prev.includes(item) ? prev.filter(x => x !== item) : [...prev, item]
    );
  };

  const handleImageChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      const file = e.target.files[0];
      if (file.size > 10 * 1024 * 1024) {
        setErrorMsg("Image size exceeds maximum limit of 10 MB.");
        return;
      }
      setImageFile(file);
      const url = URL.createObjectURL(file);
      setImagePreview(url);
      setErrorMsg(null);

      // Perform local zero-cost client-side OCR extraction with Tesseract.js
      setIsOcrProcessing(true);
      setOcrProgress('Initializing OCR engine...');

      try {
        const worker = await createWorker('eng');
        setOcrProgress('Recognizing text on label image...');
        const ret = await worker.recognize(file);
        await worker.terminate();

        const extracted = ret.data.text.trim();
        if (extracted) {
          setRawText(extracted);
          setOcrProgress('Text extracted successfully! Review or edit below.');
        } else {
          setOcrProgress('Could not detect distinct text. You can type or paste ingredients directly.');
        }
      } catch (err) {
        console.error('OCR Error:', err);
        setOcrProgress('OCR processing was unable to read the image cleanly. Please enter the ingredients manually.');
      } finally {
        setIsOcrProcessing(false);
      }
    }
  };

  const handleAnalyze = async () => {
    if (!rawText.trim()) {
      setErrorMsg("Please provide an ingredient list or upload a label photo.");
      return;
    }

    setIsAnalyzing(true);
    setErrorMsg(null);

    try {
      const data = await api.analyzeText({
        rawText,
        productName: productName.trim() || undefined,
        brand: brand.trim() || undefined,
        inputType: imageFile ? 'IMAGE_OCR' : 'MANUAL',
        lang: language,
        allergens: selectedAllergens
      }, token);

      setResult(data);
    } catch (err: any) {
      setErrorMsg(err.message || 'An error occurred while analyzing the ingredients.');
    } finally {
      setIsAnalyzing(false);
    }
  };

  const playSpeech = (text: string) => {
    if ('speechSynthesis' in window) {
      window.speechSynthesis.cancel();
      const utterance = new SpeechSynthesisUtterance(text);
      utterance.lang = language === 'ta' ? 'ta-IN' : language === 'te' ? 'te-IN' : language === 'hi' ? 'hi-IN' : language === 'kn' ? 'kn-IN' : 'en-US';
      window.speechSynthesis.speak(utterance);
    }
  };

  return (
    <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-10">
      <div className="space-y-2">
        <h1 className="text-3xl font-extrabold text-[#1D2923] tracking-tight">{t('analyzeLabel')}</h1>
        <p className="text-sm text-[#66736B]">
          Paste printed package ingredients or scan a photograph using real local OCR. Review and analyze with zero third-party dependencies.
        </p>
      </div>

      {/* Input Method Selector */}
      <div className="bg-white rounded-2xl border border-[#E1E8E2] p-6 shadow-xs space-y-6">
        <div className="flex border-b border-[#E1E8E2] pb-4 space-x-6">
          <button
            onClick={() => setActiveTab('manual')}
            className={`flex items-center space-x-2 text-sm font-semibold pb-2 border-b-2 transition-colors ${
              activeTab === 'manual' 
                ? 'border-[#176B45] text-[#176B45]' 
                : 'border-transparent text-[#66736B] hover:text-[#1D2923]'
            }`}
          >
            <FileText className="w-4 h-4" />
            <span>{t('manualEntry')}</span>
          </button>
          <button
            onClick={() => setActiveTab('upload')}
            className={`flex items-center space-x-2 text-sm font-semibold pb-2 border-b-2 transition-colors ${
              activeTab === 'upload' 
                ? 'border-[#176B45] text-[#176B45]' 
                : 'border-transparent text-[#66736B] hover:text-[#1D2923]'
            }`}
          >
            <UploadCloud className="w-4 h-4" />
            <span>{t('imageUpload')} (Local OCR)</span>
          </button>
        </div>

        {/* Optional Metadata */}
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-xs font-semibold text-[#1D2923] mb-1">Product Name (Optional)</label>
            <input
              type="text"
              value={productName}
              onChange={e => setProductName(e.target.value)}
              placeholder="e.g. Marie Biscuits, Butter Cookies..."
              className="w-full px-3 py-2 text-xs border border-[#E1E8E2] rounded-lg bg-[#FAFAF6] focus:bg-white focus:outline-none focus:border-[#176B45]"
            />
          </div>
          <div>
            <label className="block text-xs font-semibold text-[#1D2923] mb-1">Brand Name (Optional)</label>
            <input
              type="text"
              value={brand}
              onChange={e => setBrand(e.target.value)}
              placeholder="e.g. Britannia, Parle, Nestle..."
              className="w-full px-3 py-2 text-xs border border-[#E1E8E2] rounded-lg bg-[#FAFAF6] focus:bg-white focus:outline-none focus:border-[#176B45]"
            />
          </div>
        </div>

        {/* Allergen Checkbox Preferences */}
        <div>
          <label className="block text-xs font-semibold text-[#1D2923] mb-2">Check Against Allergens:</label>
          <div className="flex flex-wrap gap-2">
            {['Milk', 'Peanut', 'Soy', 'Wheat', 'Egg', 'Tree nuts', 'Gluten'].map(allergen => (
              <button
                key={allergen}
                type="button"
                onClick={() => toggleAllergen(allergen)}
                className={`px-3 py-1 rounded-full text-xs font-medium border transition-colors ${
                  selectedAllergens.includes(allergen)
                    ? 'bg-[#176B45] text-white border-[#176B45]'
                    : 'bg-[#FAFAF6] text-[#66736B] border-[#E1E8E2] hover:border-[#35A66F]'
                }`}
              >
                {selectedAllergens.includes(allergen) ? '✓ ' : '+ '}
                {allergen}
              </button>
            ))}
          </div>
        </div>

        {/* Tab 1: Image Upload (OCR) */}
        {activeTab === 'upload' && (
          <div className="space-y-4 pt-2">
            <div className="border-2 border-dashed border-[#E1E8E2] hover:border-[#176B45] rounded-xl p-6 text-center cursor-pointer bg-[#FAFAF6]/50 transition-colors">
              <input
                type="file"
                accept="image/jpeg,image/png,image/webp"
                onChange={handleImageChange}
                className="hidden"
                id="label-file-input"
              />
              <label htmlFor="label-file-input" className="cursor-pointer space-y-2 block">
                <UploadCloud className="w-8 h-8 text-[#176B45] mx-auto" />
                <span className="block text-xs font-semibold text-[#1D2923]">Click or drag label image to extract text</span>
                <span className="block text-[11px] text-[#66736B]">Supports JPG, PNG, WebP up to 10MB</span>
              </label>
            </div>

            {imagePreview && (
              <div className="flex items-center space-x-4 p-3 bg-white border border-[#E1E8E2] rounded-xl">
                <img src={imagePreview} alt="Uploaded Label" className="w-16 h-16 object-cover rounded-lg border" />
                <div className="text-xs">
                  <span className="font-semibold text-[#1D2923] block">{imageFile?.name}</span>
                  <span className="text-[#66736B]">{((imageFile?.size || 0) / (1024 * 1024)).toFixed(2)} MB</span>
                  {ocrProgress && <p className="text-[11px] text-[#176B45] font-medium mt-1">{ocrProgress}</p>}
                </div>
              </div>
            )}
          </div>
        )}

        {/* Text Area for Ingredients (Always editable) */}
        <div>
          <label className="block text-xs font-semibold text-[#1D2923] mb-1">
            Ingredient List Text (Extracted or Pasted):
          </label>
          <textarea
            rows={5}
            value={rawText}
            onChange={e => setRawText(e.target.value)}
            placeholder="Paste printed list: e.g., Refined Wheat Flour, Palm Oil, Sugar, Emulsifier (INS 322), Raising Agent (INS 500(ii)), Preservative (INS 211)..."
            className="w-full p-3 text-xs border border-[#E1E8E2] rounded-xl bg-[#FAFAF6] focus:bg-white focus:outline-none focus:border-[#176B45] font-mono leading-relaxed"
          />
        </div>

        {errorMsg && (
          <div className="p-3 bg-[#FDF2F2] border border-[#C43E3E]/20 text-[#C43E3E] rounded-xl text-xs flex items-center space-x-2">
            <AlertTriangle className="w-4 h-4 shrink-0" />
            <span>{errorMsg}</span>
          </div>
        )}

        <div className="flex justify-end">
          <button
            onClick={handleAnalyze}
            disabled={isAnalyzing || isOcrProcessing}
            className="px-6 py-2.5 bg-[#176B45] hover:bg-[#104B33] disabled:bg-gray-400 text-white rounded-xl text-xs font-semibold shadow-xs flex items-center space-x-2 transition-all cursor-pointer"
          >
            {isAnalyzing ? (
              <>
                <RefreshCw className="w-3.5 h-3.5 animate-spin" />
                <span>{t('analyzing')}</span>
              </>
            ) : (
              <>
                <span>{t('analyzeBtn')}</span>
                <ArrowRight className="w-3.5 h-3.5" />
              </>
            )}
          </button>
        </div>
      </div>

      {/* Analysis Results Display */}
      {result && (
        <div className="space-y-8 animate-fadeIn">
          {/* Header Summary */}
          <div className="bg-white p-6 rounded-2xl border border-[#E1E8E2] shadow-xs space-y-4">
            <div className="flex flex-col sm:flex-row sm:items-center justify-between pb-4 border-b border-[#E1E8E2] gap-2">
              <div>
                <h2 className="text-xl font-bold text-[#1D2923]">{result.productName}</h2>
                {result.brand && <p className="text-xs text-[#66736B]">Brand: {result.brand}</p>}
              </div>
              <div className="flex items-center space-x-2 text-xs">
                <span className="px-2.5 py-1 bg-[#EAF6EE] text-[#176B45] font-semibold rounded-md">
                  {result.identifiedIngredients} / {result.totalIngredients} {t('identified')}
                </span>
                <span className="px-2.5 py-1 bg-gray-100 text-[#1D2923] rounded-md font-mono">
                  Lang: {language.toUpperCase()}
                </span>
              </div>
            </div>

            {/* Declared Allergen Warnings */}
            {result.alerts && result.alerts.length > 0 && (
              <div className="p-4 bg-[#FFF8E6] border border-[#D99216]/40 rounded-xl space-y-2">
                <div className="flex items-center space-x-2 text-[#D99216] font-bold text-xs">
                  <AlertTriangle className="w-4 h-4 shrink-0" />
                  <span>{t('allergenAlerts')} ({result.alerts.length} Detected)</span>
                </div>
                <div className="space-y-1 pl-6">
                  {result.alerts.map((al, idx) => (
                    <div key={idx} className="text-xs text-[#1D2923]">
                      <span className="font-semibold text-[#C43E3E]">• {al.originalTerm}:</span> {al.explanation}
                    </div>
                  ))}
                </div>
              </div>
            )}
          </div>

          {/* Detailed Ingredient Breakdown */}
          <div className="space-y-4">
            <h3 className="text-lg font-bold text-[#1D2923]">Ingredient Breakdown & Localized Explanations</h3>

            <div className="grid grid-cols-1 gap-4">
              {result.items.map((item, idx) => (
                <div
                  key={idx}
                  className={`bg-white p-5 rounded-2xl border shadow-xs transition-all ${
                    item.status === 'IDENTIFIED' 
                      ? 'border-[#E1E8E2] hover:border-[#35A66F]' 
                      : 'border-amber-200 bg-amber-50/20'
                  }`}
                >
                  <div className="flex flex-col sm:flex-row sm:items-center justify-between pb-3 border-b border-[#E1E8E2]/60 gap-2">
                    <div className="flex items-center space-x-2">
                      <span className="text-sm font-bold text-[#1D2923]">
                        {item.canonicalName || item.originalTerm}
                      </span>
                      {item.additiveCode && (
                        <span className="px-2 py-0.5 bg-[#EAF6EE] text-[#176B45] text-[11px] font-mono font-bold rounded">
                          {item.additiveCode}
                        </span>
                      )}
                      {item.category && (
                        <span className="px-2 py-0.5 bg-gray-100 text-[#66736B] text-[10px] font-semibold rounded uppercase">
                          {item.category}
                        </span>
                      )}
                    </div>

                    <div className="flex items-center space-x-2">
                      {item.status === 'IDENTIFIED' ? (
                        <span className="inline-flex items-center text-[11px] text-[#176B45] font-semibold">
                          <CheckCircle className="w-3.5 h-3.5 mr-1" />
                          Verified
                        </span>
                      ) : (
                        <span className="inline-flex items-center text-[11px] text-[#D99216] font-semibold">
                          <HelpCircle className="w-3.5 h-3.5 mr-1" />
                          Unverified
                        </span>
                      )}
                      {item.localizedExplanation && (
                        <button
                          onClick={() => playSpeech(`${item.canonicalName || item.originalTerm}. ${item.localizedExplanation}`)}
                          className="p-1 hover:bg-gray-100 rounded text-[#176B45]"
                          title="Listen explanation"
                        >
                          <Volume2 className="w-4 h-4" />
                        </button>
                      )}
                    </div>
                  </div>

                  <div className="pt-3 space-y-2 text-xs">
                    {item.originalTerm !== item.canonicalName && (
                      <p className="text-[#66736B]">
                        <span className="font-semibold">Printed on label:</span> "{item.originalTerm}"
                      </p>
                    )}
                    <p className="text-[#1D2923] leading-relaxed">
                      <span className="font-semibold text-[#176B45]">Explanation:</span> {item.localizedExplanation}
                    </p>
                    {item.localizedPurpose && (
                      <p className="text-[#66736B] leading-relaxed">
                        <span className="font-semibold text-[#1D2923]">Functional Purpose:</span> {item.localizedPurpose}
                      </p>
                    )}
                    {item.localizedPrecautions && (
                      <p className="text-[#C43E3E] leading-relaxed">
                        <span className="font-semibold">Precautions:</span> {item.localizedPrecautions}
                      </p>
                    )}
                    {item.sources && (
                      <p className="text-[10px] text-[#8C9891]">
                        <span className="font-semibold">Source:</span> {item.sources}
                      </p>
                    )}
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
