/**
 * useAdminAuth — client-side-only admin session gate.
 *
 * There is no Spring Security / token verification behind this yet — it
 * just remembers the Admin record returned by POST /admin/login, so the
 * /admindashboard routes can be blocked until someone has been through
 * the /admin login screen.
 *
 * "Remember me" decides where that record is kept: localStorage survives
 * browser restarts, sessionStorage clears when the tab closes. Only one
 * of the two is ever written at a time so there's a single source of truth.
 */
import { ref, computed } from "vue";

const STORAGE_KEY = "gfc_admin_session";

function readStoredAdmin() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY) || sessionStorage.getItem(STORAGE_KEY);
    return JSON.parse(raw || "null");
  } catch {
    return null;
  }
}

const admin = ref(readStoredAdmin());

function login(adminRecord, remember = false) {
  admin.value = adminRecord;
  const json = JSON.stringify(adminRecord);

  if (remember) {
    localStorage.setItem(STORAGE_KEY, json);
    sessionStorage.removeItem(STORAGE_KEY);
  } else {
    sessionStorage.setItem(STORAGE_KEY, json);
    localStorage.removeItem(STORAGE_KEY);
  }
}

function logout() {
  admin.value = null;
  localStorage.removeItem(STORAGE_KEY);
  sessionStorage.removeItem(STORAGE_KEY);
}

// Refreshes the cached session record after the admin edits their own
// profile or password, writing back to whichever storage already holds it.
function updateAdmin(adminRecord) {
  admin.value = adminRecord;
  const json = JSON.stringify(adminRecord);

  if (localStorage.getItem(STORAGE_KEY) !== null) {
    localStorage.setItem(STORAGE_KEY, json);
  } else {
    sessionStorage.setItem(STORAGE_KEY, json);
  }
}

// Plain function (not a computed) so the router guard can call it outside
// component context without importing the whole composable API.
export function isAdminLoggedIn() {
  return !!admin.value;
}

export function useAdminAuth() {
  return {
    admin,
    isLoggedIn: computed(() => !!admin.value),
    login,
    logout,
    updateAdmin,
  };
}
