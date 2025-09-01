#!/usr/bin/env python3
import csv
import sys
from collections import Counter, defaultdict

# Prices in pence
PRICE = {"Apple": 35, "Banana": 20, "Melon": 50, "Lime": 15}

def main():
    if len(sys.argv) < 2:
        print("Usage: analyze_simple.py carts.csv")
        sys.exit(1)

    path = sys.argv[1]
    item_counter = Counter()
    cart_totals = {}

    # Read rows
    carts = defaultdict(list)
    with open(path, newline="", encoding="utf-8") as f:
        reader = csv.DictReader(f)
        for row in reader:
            cart_id = row["cart_id"]
            item = row["item"]
            carts[cart_id].append(item)
            item_counter[item] += 1

    # Calculate totals
    for cart_id, items in carts.items():
        counts = Counter(items)
        total = 0
        for name, count in counts.items():
            if name not in PRICE:
                print(f"Unknown item: {name}")
                continue
            if name == "Melon":  # BOGO
                count = count - (count // 2)
            elif name == "Lime":  # 3-for-2
                count = count - (count // 3)
            total += PRICE[name] * count
        cart_totals[cart_id] = total

    # Print results
    print("Top 5 items:")
    for name, cnt in item_counter.most_common(5):
        print(f"  {name}: {cnt}")

    print("\nCart totals (first 10 carts):")
    for cart_id, total in list(cart_totals.items())[:10]:
        print(f"  {cart_id}: £{total/100:.2f}")

if __name__ == "__main__":
    main()
