// Mirrors za.ac.cput.guiltfreecookie.domain.CookieCategory on the backend.
export const COOKIE_CATEGORIES = [
  "LOW_SUGAR",
  "DIABETIC_FRIENDLY",
  "HIGH_PROTEIN",
  "GLUTEN_FREE",
  "VEGAN",
  "CLASSIC",
];

export const formatCategoryLabel = (category) =>
  category ? category.replace(/_/g, " ") : "";
