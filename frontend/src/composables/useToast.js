/**
 * useToast — tiny global toast notification store (no external deps).
 * Every importer shares the same `toasts` array, so any component can
 * push a toast and a single <ToastContainer /> renders them all.
 */
import { reactive } from "vue";

const toasts = reactive([]);
let nextId = 0;

function removeToast(id) {
  const index = toasts.findIndex((toast) => toast.id === id);
  if (index !== -1) toasts.splice(index, 1);
}

function showToast(message, type = "success", duration = 3000) {
  const id = ++nextId;
  toasts.push({ id, message, type });
  setTimeout(() => removeToast(id), duration);
}

export function useToast() {
  return { toasts, showToast, removeToast };
}
