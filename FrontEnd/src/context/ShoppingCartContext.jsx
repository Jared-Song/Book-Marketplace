import _, { set } from "lodash";
import React from "react";

const ShoppingCartContext = React.createContext(null);

export const ShoppingCartProvider = ({ children }) => {
  console.log(typeof window)
  const storageItem = typeof window !== "undefined" ? localStorage.getItem("cart") : undefined;
  console.log(storageItem)
  const [items, setItems] = React.useState(storageItem ? JSON.parse(storageItem) : []);
  React.useEffect(() => {
    localStorage.setItem("cart", JSON.stringify(items));
  }, [items]);
  const removeAllItems = () => {
    setItems([])
    localStorage.clear()
  }

  const removeShoppingCartItem = (id) => {
    console.log(id)
    const updatedItems = _.remove(items, (item) => {
      return item.id !== id
    })
    console.log(updatedItems)
    setItems(updatedItems)
  }
  const editShoppingCartItem = (newItem) => {
    const updatedItems = items.map((item) => {
      if (newItem.id === item.id) {
        return newItem; 
      }
      return item
    })
    setItems(updatedItems);
  }
  const addIntoShoppingCart = (newItem) => {
    const itemExist = items.filter((item) => item.id === newItem.id);
    if (itemExist.length > 0) {
      const updatedItems = items.map((item) => {
        if (item.id === newItem.id) {
          return {
            ...item,
            quantity: item.quantity + parseInt(newItem.quantity)
          }
        }
        return item;
      })
      setItems(updatedItems);
    } else {
      setItems([
        ...items,
        {
          ...newItem,
          quantity: parseInt(newItem.quantity),
        },
      ]);
    }
  }
  return (
    <ShoppingCartContext.Provider
      value={{
        cartItems: items,
        addIntoShoppingCart,
        editShoppingCartItem,
        removeShoppingCartItem,
        removeAllItems,
      }}
    >
      {children}
    </ShoppingCartContext.Provider>
  );
}

export const useShoppingCart = () => React.useContext(ShoppingCartContext);