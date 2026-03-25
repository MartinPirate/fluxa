"use client";

export function GradientOrbs() {
  return (
    <div className="absolute inset-0 overflow-hidden pointer-events-none" aria-hidden="true">
      <div
        className="absolute w-[600px] h-[600px] rounded-full opacity-[0.15] blur-[120px]"
        style={{
          background: "radial-gradient(circle, #2563eb 0%, transparent 70%)",
          top: "-200px",
          right: "-100px",
          animation: "orb-drift 12s ease-in-out infinite alternate",
        }}
      />
      <div
        className="absolute w-[500px] h-[500px] rounded-full opacity-[0.12] blur-[100px]"
        style={{
          background: "radial-gradient(circle, #7c3aed 0%, transparent 70%)",
          bottom: "-100px",
          left: "-150px",
          animation: "orb-drift 15s ease-in-out infinite alternate-reverse",
        }}
      />
      <div
        className="absolute w-[400px] h-[400px] rounded-full opacity-[0.08] blur-[80px]"
        style={{
          background: "radial-gradient(circle, #06b6d4 0%, transparent 70%)",
          top: "40%",
          left: "50%",
          animation: "orb-drift 18s ease-in-out infinite alternate",
        }}
      />
    </div>
  );
}
