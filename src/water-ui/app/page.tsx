"use client";
import React, { useState } from 'react';
import { Send, Droplets, Bot, User } from 'lucide-react';

export default function WaterChat() {
  const [messages, setMessages] = useState([{ role: 'bot', text: 'Hello! I am your CA Water Assistant. How can I help with your bill today?' }]);
  const [input, setInput] = useState('');
  const [loading, setLoading] = useState(false);

 const handleSend = async () => {
    if (!input.trim()) return;
    setLoading(true);

    try {
      // 1. Get the dynamic URL from our internal config endpoint
      const configRes = await fetch('/api/config');
      const { backendUrl } = await configRes.json();

      // 2. Use that URL for the actual AI chat call
      const res = await fetch(`${backendUrl}/api/water/chat`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ message: input }),
      });

      const data = await res.json();
      setMessages(prev => [...prev, { role: 'user', text: input }, { role: 'bot', text: data.answer }]);
    } catch (err) {
      setMessages(prev => [...prev, { role: 'bot', text: "Error connecting to service." }]);
    } finally {
      setLoading(false);
      setInput('');
    }
  };

  return (
    <div className="flex flex-col h-screen bg-slate-50 font-sans">
      {/* Header */}
      <header className="bg-blue-600 p-4 text-white flex items-center gap-3 shadow-lg">
        <Droplets className="w-8 h-8" />
        <h1 className="text-xl font-bold tracking-tight">California Water Intelligence</h1>
      </header>

      {/* Chat Area */}
      <main className="flex-1 overflow-y-auto p-6 space-y-4">
        {messages.map((m, i) => (
          <div key={i} className={`flex ${m.role === 'user' ? 'justify-end' : 'justify-start'}`}>
            <div className={`max-w-[80%] p-4 rounded-2xl shadow-sm flex gap-3 ${
              m.role === 'user' ? 'bg-blue-500 text-white' : 'bg-white text-slate-800'
            }`}>
              {m.role === 'bot' ? <Bot className="w-5 h-5 mt-1" /> : <User className="w-5 h-5 mt-1" />}
              <p className="leading-relaxed">{m.text}</p>
            </div>
          </div>
        ))}
        {loading && <div className="text-slate-400 animate-pulse flex gap-2"><Bot className="w-5 h-5"/> Thinking...</div>}
      </main>

      {/* Input Field */}
      <footer className="p-4 bg-white border-t border-slate-200">
        <div className="max-w-4xl mx-auto flex gap-2">
          <input 
            className="flex-1 border border-slate-300 rounded-full px-6 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Ask about tiered rates, drought surcharges..."
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={(e) => e.key === 'Enter' && handleSend()}
          />
          <button 
            onClick={handleSend}
            className="bg-blue-600 hover:bg-blue-700 text-white p-3 rounded-full transition-colors"
          >
            <Send className="w-6 h-6" />
          </button>
        </div>
      </footer>
    </div>
  );
}