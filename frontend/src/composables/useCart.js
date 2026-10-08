import { computed, ref } from "vue";
import cartApi from "../api/cartApi";
import cookieApi from "../api/cookieApi";

const CART_ID_KEY = "guilt-free-cookie-cart-id";
const CART_ITEMS_KEY = "guilt-free-cookie-cart-items";
const CUSTOMER_EMAIL_KEY = "guilt-free-cookie-cart-customer";

function readItems() {
  try {
    const stored = JSON.parse(localStorage.getItem(CART_ITEMS_KEY) || "[]");
    return Array.isArray(stored) ? stored : [];
  } catch {
    return [];
  }
}

function createCartId() {
  return globalThis.crypto?.randomUUID?.()
    || `cart-${Date.now()}-${Math.random().toString(36).slice(2)}`;
}

const cartId = ref(localStorage.getItem(CART_ID_KEY) || "");
const items = ref(readItems());
const customerEmail = ref(localStorage.getItem(CUSTOMER_EMAIL_KEY) || "");
const itemCount = computed(() => items.value.reduce((total, item) => total + item.quantity, 0));
const subtotal = computed(() => items.value.reduce(
  (total, item) => total + Number(item.cookie.price || 0) * item.quantity,
  0,
));

function ensureCartId() {
  if (!cartId.value) {
    cartId.value = createCartId();
    localStorage.setItem(CART_ID_KEY, cartId.value);
  }
  return cartId.value;
}

function persistItems() {
  localStorage.setItem(CART_ITEMS_KEY, JSON.stringify(items.value));
}

async function addItem(cookie, quantity = 1) {
  const id = ensureCartId();
  const existing = items.value.find((item) => item.cookie.cookieId === cookie.cookieId);
  if (existing) {
    existing.quantity += quantity;
  } else {
    items.value.push({ cookie, quantity });
  }
  persistItems();

  try {
    await cartApi.addCookie(id, cookie.cookieId);
    return true;
  } catch {
    return false;
  }
}

async function loadCart() {
  if (!cartId.value) return;
  try {
    const links = await cartApi.getCookieCarts(cartId.value);
    const cachedById = new Map(items.value.map((item) => [item.cookie.cookieId, item]));
    const remoteItems = await Promise.all(links.map(async (link) => {
      const cached = cachedById.get(link.cookieId);
      const cookie = cached?.cookie || await cookieApi.getById(link.cookieId);
      return { cookie, quantity: cached?.quantity || 1 };
    }));
    const remoteIds = new Set(remoteItems.map((item) => item.cookie.cookieId));
    items.value = [
      ...remoteItems,
      ...items.value.filter((item) => !remoteIds.has(item.cookie.cookieId)),
    ];
    persistItems();
  } catch {
    // Keep the local cart available when the API is offline.
  }
}

async function removeItem(cookieId) {
  items.value = items.value.filter((item) => item.cookie.cookieId !== cookieId);
  persistItems();
  if (!cartId.value) return true;
  try {
    await cartApi.removeCookie(cartId.value, cookieId);
    return true;
  } catch {
    return false;
  }
}

function setQuantity(cookieId, quantity) {
  const item = items.value.find((entry) => entry.cookie.cookieId === cookieId);
  if (!item) return;
  item.quantity = Math.max(1, Number(quantity) || 1);
  persistItems();
}

async function saveCustomer(email) {
  const id = ensureCartId();
  try {
    await cartApi.linkCustomer(id, email.trim().toLowerCase());
    customerEmail.value = email.trim().toLowerCase();
    localStorage.setItem(CUSTOMER_EMAIL_KEY, customerEmail.value);
    return true;
  } catch {
    return false;
  }
}

export function useCart() {
  return {
    cartId,
    items,
    customerEmail,
    itemCount,
    subtotal,
    addItem,
    loadCart,
    removeItem,
    setQuantity,
    saveCustomer,
  };
}
