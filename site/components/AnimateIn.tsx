"use client";

import { useEffect, useRef, useState } from "react";

type AnimateInProps = {
  children: React.ReactNode;
  className?: string;
  delay?: number;
  direction?: "up" | "down" | "left" | "right" | "none";
  duration?: number;
  once?: boolean;
};

export function AnimateIn({
  children,
  className = "",
  delay = 0,
  direction = "up",
  duration = 700,
  once = true,
}: AnimateInProps) {
  const ref = useRef<HTMLDivElement>(null);
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => {
    const observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          setIsVisible(true);
          if (once && ref.current) observer.unobserve(ref.current);
        }
      },
      { threshold: 0.1, rootMargin: "0px 0px -40px 0px" }
    );
    if (ref.current) observer.observe(ref.current);
    return () => observer.disconnect();
  }, [once]);

  const translateMap = {
    up: "translateY(32px)",
    down: "translateY(-32px)",
    left: "translateX(32px)",
    right: "translateX(-32px)",
    none: "none",
  };

  return (
    <div
      ref={ref}
      className={className}
      style={{
        opacity: isVisible ? 1 : 0,
        transform: isVisible ? "none" : translateMap[direction],
        transition: `opacity ${duration}ms cubic-bezier(0.16, 1, 0.3, 1) ${delay}ms, transform ${duration}ms cubic-bezier(0.16, 1, 0.3, 1) ${delay}ms`,
        willChange: "opacity, transform",
      }}
    >
      {children}
    </div>
  );
}

type StaggerChildrenProps = {
  children: React.ReactNode[];
  className?: string;
  stagger?: number;
  direction?: "up" | "down" | "left" | "right" | "none";
  duration?: number;
  baseDelay?: number;
};

export function StaggerChildren({
  children,
  className = "",
  stagger = 80,
  direction = "up",
  duration = 600,
  baseDelay = 0,
}: StaggerChildrenProps) {
  return (
    <div className={className}>
      {children.map((child, i) => (
        <AnimateIn
          key={i}
          delay={baseDelay + i * stagger}
          direction={direction}
          duration={duration}
        >
          {child}
        </AnimateIn>
      ))}
    </div>
  );
}
