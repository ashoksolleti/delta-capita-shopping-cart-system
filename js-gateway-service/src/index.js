import express from "express";
import axios from "axios";
import morgan from "morgan";

const app = express();
app.use(express.json());
app.use(morgan("dev"));

// Backend Java API URL
const JAVA_API_URL = process.env.JAVA_API_URL || "http://localhost:8080";

// In-memory cart store (demo only)
const carts = new Map();

/**
 * Add item to cart
 */
app.post("/api/cart/:cartId/add", (req, res) => {
  const { cartId } = req.params;
  const { item } = req.body || {};
  if (!item || typeof item !== "string") {
    return res.status(400).json({
      success: false,
      message: "Body must include 'item' string",
    });
  }
  const items = [...(carts.get(cartId) || []), item];
  carts.set(cartId, items);
  res.json({ success: true, cartId, items });
});

/**
 * View cart
 */
app.get("/api/cart/:cartId/view", (req, res) => {
  const { cartId } = req.params;
  res.json({ success: true, cartId, items: carts.get(cartId) || [] });
});

/**
 * Get total price from Java backend
 */
app.post("/api/cart/:cartId/total", async (req, res) => {
  const { cartId } = req.params;
  const items = carts.get(cartId) || [];

  try {
    const resp = await axios.post(
      `${JAVA_API_URL}/api/v1/cart/total`,
      { items },
      { timeout: 5000 }
    );
    res.json({ success: true, cartId, items, total: resp.data });
  } catch (err) {
    if (err.response) {
      return res.status(err.response.status).json({
        success: false,
        message: "Backend error",
        detail: err.response.data,
      });
    }
    return res.status(502).json({
      success: false,
      message: "Unable to connect backend service",
      detail: err.message,
    });
  }
});

// 404 handler
app.use((req, res) => res.status(404).json({ success: false, message: "Not found" }));

// Start server
const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`JS API listening on :${port} (forwarding to ${JAVA_API_URL})`);
});
