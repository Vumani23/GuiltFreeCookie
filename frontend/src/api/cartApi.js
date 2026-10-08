import axios from "axios";

const API_BASE = "http://localhost:8080/guiltFreeCookie/api/v1";

export default {
  async getCookieCarts(cartId) {
    const response = await axios.get(`${API_BASE}/cookie-carts/${encodeURIComponent(cartId)}`);
    return response.data;
  },

  async addCookie(cartId, cookieId) {
    const response = await axios.post(`${API_BASE}/cookie-carts`, { cartId, cookieId });
    return response.data;
  },

  async removeCookie(cartId, cookieId) {
    await axios.delete(
      `${API_BASE}/cookie-carts/${encodeURIComponent(cartId)}/${encodeURIComponent(cookieId)}`,
    );
  },

  async linkCustomer(cartId, customerEmail) {
    const response = await axios.post(`${API_BASE}/customer-carts`, {
      cartId,
      customerEmail,
    });
    return response.data;
  },
};
