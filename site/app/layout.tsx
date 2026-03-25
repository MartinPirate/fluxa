import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "Fluxa — The Android Framework That Feels Right",
  description:
    "Build polished Android screens faster with utility-first styling, typed navigation, and opinionated defaults. Fluxa sits on top of Compose so you can ship better UI without fighting the platform.",
  openGraph: {
    title: "Fluxa — The Android Framework That Feels Right",
    description:
      "Utility-first styling. Typed navigation. Opinionated defaults. Build Android apps that look premium without the boilerplate.",
    type: "website",
  },
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <head>
        <link rel="icon" href="/icon.svg" type="image/svg+xml" />
        <link
          href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap"
          rel="stylesheet"
        />
        <link
          href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;500;600&display=swap"
          rel="stylesheet"
        />
      </head>
      <body className="antialiased">{children}</body>
    </html>
  );
}
