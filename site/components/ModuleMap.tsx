const modules = [
  {
    name: "fluxa-runtime",
    layer: "Core",
    description: "Style spec IR, breakpoints, version",
    color: "bg-blue-100 text-blue-800 border-blue-200",
  },
  {
    name: "fluxa-style",
    layer: "Core",
    description: "Utility DSL, tokens, themes, variants, motion",
    color: "bg-blue-100 text-blue-800 border-blue-200",
  },
  {
    name: "fluxa-ui",
    layer: "Core",
    description: "Node primitives, components, forms, lists",
    color: "bg-blue-100 text-blue-800 border-blue-200",
  },
  {
    name: "fluxa-compose",
    layer: "Bridge",
    description: "Compose renderer, theme provider, preview helpers",
    color: "bg-purple-100 text-purple-800 border-purple-200",
  },
  {
    name: "fluxa-state",
    layer: "App",
    description: "Observable state, store, reducers, selectors",
    color: "bg-emerald-100 text-emerald-800 border-emerald-200",
  },
  {
    name: "fluxa-effect",
    layer: "App",
    description: "Lifecycle effects, polling, collectAsState",
    color: "bg-emerald-100 text-emerald-800 border-emerald-200",
  },
  {
    name: "fluxa-nav",
    layer: "App",
    description: "Typed routes, back stack, animated transitions",
    color: "bg-emerald-100 text-emerald-800 border-emerald-200",
  },
  {
    name: "fluxa-data",
    layer: "App",
    description: "HTTP client, interceptors, auth, retry",
    color: "bg-emerald-100 text-emerald-800 border-emerald-200",
  },
  {
    name: "fluxa-cache",
    layer: "App",
    description: "In-memory TTL cache with eviction",
    color: "bg-emerald-100 text-emerald-800 border-emerald-200",
  },
  {
    name: "fluxa-store",
    layer: "App",
    description: "DataStore persistence, typed preferences",
    color: "bg-emerald-100 text-emerald-800 border-emerald-200",
  },
  {
    name: "fluxa-work",
    layer: "App",
    description: "Background tasks, periodic scheduling",
    color: "bg-emerald-100 text-emerald-800 border-emerald-200",
  },
  {
    name: "fluxa-test",
    layer: "Dev",
    description: "Node, style, and resource assertions",
    color: "bg-amber-100 text-amber-800 border-amber-200",
  },
  {
    name: "fluxa-cli",
    layer: "Dev",
    description: "Project scaffolding and generators",
    color: "bg-amber-100 text-amber-800 border-amber-200",
  },
];

const layers = ["Core", "Bridge", "App", "Dev"];

export function ModuleMap() {
  return (
    <div className="space-y-6">
      {layers.map((layer) => {
        const layerModules = modules.filter((m) => m.layer === layer);
        return (
          <div key={layer}>
            <div className="text-xs font-semibold uppercase tracking-wider text-[var(--aurora-text-secondary)] mb-3">
              {layer} Layer
            </div>
            <div className="flex flex-wrap gap-3">
              {layerModules.map((mod) => (
                <div
                  key={mod.name}
                  className={`px-4 py-3 rounded-xl border ${mod.color} text-sm`}
                >
                  <div className="font-mono font-semibold text-xs">
                    {mod.name}
                  </div>
                  <div className="mt-1 opacity-75 text-xs">
                    {mod.description}
                  </div>
                </div>
              ))}
            </div>
          </div>
        );
      })}
    </div>
  );
}
