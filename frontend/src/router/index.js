import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import ProductListView from "../views/ProductListView.vue";
import ProductDetailView from "../views/ProductDetailView.vue";
import AdminLoginView from "../views/AdminLoginView.vue";
import AdminDashboardView from "../views/AdminDashboardView.vue";
import AdminUsersView from "../views/AdminUsersView.vue";
import AdminOrdersView from "../views/AdminOrdersView.vue";
import AdminProfileView from "../views/AdminProfileView.vue";
import { isAdminLoggedIn } from "../composables/useAdminAuth";

const routes = [
  {
    path: "/",
    name: "Home",
    component: HomeView,
  },
  {
    path: "/products",
    name: "Products",
    component: ProductListView,
  },
  {
    path: "/product/:id",
    name: "ProductDetail",
    component: ProductDetailView,
  },
  {
    path: "/contact",
    name: "Contact",
    component: () => import("../views/ContactView.vue"),
  },
  {
    path: "/about",
    name: "About",
    component: () => import("../views/AboutView.vue"),
  },

  // Admin Routes

  {
    path: "/admin",
    name: "AdminLogin",
    component: AdminLoginView,
    meta: { title: "Admin Login", hideChrome: true },
  },
  {
    path: "/admindashboard",
    redirect: "/admindashboard/cookies",
  },
  {
    path: "/admindashboard/cookies",
    name: "AdminDashboard",
    component: AdminDashboardView,
    meta: { title: "Admin Dashboard", requiresAdmin: true },
  },
  {
    path: "/admindashboard/users",
    name: "AdminUsers",
    component: AdminUsersView,
    meta: { title: "Admin Users", requiresAdmin: true },
  },
  {
    path: "/admindashboard/orders",
    name: "AdminOrders",
    component: AdminOrdersView,
    meta: { title: "Admin Orders", requiresAdmin: true },
  },
  {
    path: "/admindashboard/profile",
    name: "AdminProfile",
    component: AdminProfileView,
    meta: { title: "My Profile", requiresAdmin: true },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 };
  },
});

router.beforeEach((to) => {
  if (to.meta.requiresAdmin && !isAdminLoggedIn()) {
    return { name: "AdminLogin", query: { redirect: to.fullPath } };
  }

  if (to.name === "AdminLogin" && isAdminLoggedIn()) {
    return { name: "AdminDashboard" };
  }
});

export default router;
