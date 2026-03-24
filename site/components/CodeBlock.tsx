"use client";

import { useState } from "react";

export function CodeBlock({
  code,
  language = "kotlin",
  filename,
}: {
  code: string;
  language?: string;
  filename?: string;
}) {
  const [copied, setCopied] = useState(false);

  const handleCopy = () => {
    navigator.clipboard.writeText(code);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  return (
    <div className="rounded-2xl overflow-hidden border border-[var(--aurora-panel-border)] bg-[var(--aurora-spotlight)] shadow-lg">
      {filename && (
        <div className="flex items-center justify-between px-4 py-2 bg-[#1a2433] border-b border-[#2E3D50]">
          <span className="text-xs text-[#8A96A6] font-mono">{filename}</span>
          <button
            onClick={handleCopy}
            className="text-xs text-[#8A96A6] hover:text-white transition-colors cursor-pointer"
          >
            {copied ? "Copied!" : "Copy"}
          </button>
        </div>
      )}
      <pre className="p-5 overflow-x-auto text-sm leading-relaxed">
        <code className="text-[#E8EEF4] font-mono">{code}</code>
      </pre>
    </div>
  );
}
