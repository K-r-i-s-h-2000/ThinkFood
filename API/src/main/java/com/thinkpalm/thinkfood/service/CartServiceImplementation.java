package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.entity.CartItem;
import com.thinkpalm.thinkfood.entity.Customer;
import com.thinkpalm.thinkfood.entity.Menu;
import com.thinkpalm.thinkfood.exception.NotAvailableException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.exception.NotSameRestaurantException;
import com.thinkpalm.thinkfood.model.Cart;
import com.thinkpalm.thinkfood.repository.CartItemRepository;
import com.thinkpalm.thinkfood.repository.CartRepository;
import com.thinkpalm.thinkfood.repository.CustomerRepository;
import com.thinkpalm.thinkfood.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation class for managing cart-related operations.
 * @author krishna.c
 * @version 31/10/2023
 * @since 31/10/2023
 */
@Log4j2
@Transactional
@Service
public class CartServiceImplementation implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Create a new cart based on the provided cart request.
     *
     * @param cartRequest The cart request containing customer and cart item information.
     * @return The created cart with updated details.
     * @throws NotFoundException           if any required entities (e.g., customer or menu) are not found.
     * @throws NotSameRestaurantException  if items in the cart are not from the same restaurant.
     * @throws NotAvailableException       if any item in the cart is not available.
     */
    @Override
    public Cart createAndUpdateCart(Cart cartRequest) throws NotFoundException, NotSameRestaurantException, NotAvailableException {
            Optional<Customer> customerEntityWrapper = customerRepository.findById(cartRequest.getCustomer());
            Cart cartResponse;
            if(cartRequest.getCart()==null){
                cartResponse = createCart(cartRequest);
            } else {
                Optional<com.thinkpalm.thinkfood.entity.Cart> existingCartOptional = cartRepository.findById(cartRequest.getCart());
                if (customerEntityWrapper.isEmpty()) {
                    log.error("Customer is not found exception is thrown.");
                    throw new NotFoundException("Customer is not found with id: " + cartRequest.getCustomer());
                }
                cartResponse = updateCart(cartRequest);

            }
            return cartResponse;

    }
    @Override
    public Cart createCart(Cart cartRequest) throws NotFoundException, NotSameRestaurantException, NotAvailableException {
        Optional<Customer> customerEntityWrapper = customerRepository.findById(cartRequest.getCustomer());

        if (customerEntityWrapper.isEmpty()) {
            log.error("Customer is not found exception is thrown.");
            throw new NotFoundException("Customer is not found with id: " + cartRequest.getCustomer());
        }
        List<com.thinkpalm.thinkfood.entity.Cart> cartOfSameCustomer = cartRepository.findByCustomer_IdAndIsActiveTrue(cartRequest.getCustomer());
        for(com.thinkpalm.thinkfood.entity.Cart sameCart : cartOfSameCustomer){
            if(sameCart.getIsActive()){
                softDeleteCart(sameCart.getId());
            }
        }
        Customer customer = customerEntityWrapper.get();
        List<CartItem> cartItems = new ArrayList<>();
        double totalAmount = 0.0;
        com.thinkpalm.thinkfood.entity.Cart cart = new com.thinkpalm.thinkfood.entity.Cart();
        cart.setCustomer(customer);
        cart.setTotalAmount(totalAmount);
        int itemRequestId = 0;
        log.info("Entering into the cartItems in the cartRequest. ");
        for (com.thinkpalm.thinkfood.model.CartItem itemRequest : cartRequest.getCartItem()) {
            Optional<Menu> menuOptional = menuRepository.findById(itemRequest.getItemId());
            Optional<Menu> firstMenuOptional = menuRepository.findById(cartRequest.getCartItem().get(0).getItemId());
            Long firstMenuRestaurantId = 0L;
            if (firstMenuOptional.isPresent()) {
                firstMenuRestaurantId = firstMenuOptional.get().getRestaurant().getId();
            }
            if (menuOptional.isEmpty()) {
                log.error("Menu is not found exception is thrown.");
                throw new NotFoundException("Menu is not found with id:" + itemRequest.getItemId());
            }

            Optional<Menu> menuOptionalLoop = menuRepository.findById(cartRequest.getCartItem().get(itemRequestId).getItemId());
            Long restaurantId = menuOptionalLoop.get().getRestaurant().getId();
            log.info("Checking whether the item is selected from the same restaurant.");
            if (firstMenuRestaurantId == restaurantId) {
                if (menuOptional.get().getItemAvailability() ) {
                    Menu menu = menuOptional.get();

                    CartItem cartItem = new CartItem();
                    cartItem.setCart(cart);
                    cartItem.setMenu(menu);
                    cartItem.setQuantity(itemRequest.getQuantity());
                    cartItem.setIsActive(true);
                    cartItem.setItemPrice(menu.getItemPrice());

                    double itemTotalAmount = itemRequest.getQuantity() * menu.getItemPrice();
                    cartItem.setSubtotal(itemTotalAmount);
                    totalAmount += itemTotalAmount;
                    cartItems.add(cartItem);

                } else {
                    log.error("Item is not available exception is thrown.");
                    throw new NotAvailableException("Item is not available at the moment or Item is not Present!");
                }
            } else {
                log.error("Item is not from the same restaurant exception is thrown.");
                throw new NotSameRestaurantException("Item is not from the same restaurant!");
            }
            itemRequestId++;

        }
        cart.setTotalAmount(totalAmount);
        cart.setIsActive(true);
        log.info("Cart is getting saved to the database.");
        cartRepository.save(cart);
        log.info("CartItems are getting saved to the database.");
        cartItemRepository.saveAll(cartItems);

        cartRequest.setCart(cart.getId());
        cartRequest.setTotalAmount(cart.getTotalAmount());
        log.info("The details of each item are being set to the model.");
        cartRequest.getCartItem().forEach(cartItem -> {
            for (CartItem savedCartItem : cartItems) {
                if (cartItem.getItemId().equals(savedCartItem.getMenu().getId())) {
                    cartItem.setId(savedCartItem.getId());
                    cartItem.setItemPrice(savedCartItem.getItemPrice());
                    cartItem.setItemName(savedCartItem.getMenu().getItem().getItemName());
                    cartItem.setSubtotal(savedCartItem.getSubtotal());
                }
            }
        });
        log.info("CartRequest model is returned in this method.");
        return cartRequest;
    }

    /**
     * Find a cart by its unique identifier.
     *
     * @param cartId The unique identifier of the cart to find.
     * @return The found cart.
     * @throws NotFoundException if the cart is not found.
     */
    @Override
    public Cart findCartById(Long cartId) throws NotFoundException {
        com.thinkpalm.thinkfood.entity.Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Cart is not found"));
        log.info("Checking if the cart is deleted or not found!");
        if(!cart.getIsActive()){
            log.error("Cart not found exception is thrown.");
            throw new NotFoundException("Cart is not found!");
        }
        Cart cartModel = new Cart();
        cartModel.setCart(cartId);
        cartModel.setCustomer(cart.getCustomer().getId());
        cartModel.setCartItem(cartModel.getCartItem());
        cartModel.setTotalAmount(cart.getTotalAmount());
        List<CartItem> cartItems = cart.getCartItems();
        log.info("Iterating through the items in the particular cart.");
        List<com.thinkpalm.thinkfood.model.CartItem> cartItemModelList = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            if(cartItem.getIsActive()){
                com.thinkpalm.thinkfood.model.CartItem cartItemList = new com.thinkpalm.thinkfood.model.CartItem();
                cartItemList.setId(cartItem.getId());
                cartItemList.setItemId(cartItem.getMenu().getId());
                cartItemList.setItemName(cartItem.getMenu().getItem().getItemName());
                cartItemList.setItemPrice(cartItem.getItemPrice());
                cartItemList.setSubtotal(cartItem.getSubtotal());
                cartItemList.setQuantity(cartItem.getQuantity());
                cartItemList.setItemImage(cartItem.getMenu().getItem().getImage());

                cartItemModelList.add(cartItemList);
            }

        }
        cartModel.setCartItem(cartItemModelList);
        log.info("Cart with the specified Id is being return to this method in a model.");
        return cartModel;
    }
    @Override
    public Cart findCartByCustomerId(Long customerId) throws NotFoundException {
        com.thinkpalm.thinkfood.entity.Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer is not found"));
        log.info("Checking if the customer is found or not!");
        if(!customer.getIsActive()){
            log.error("Customer is not found exception thrown!");
            throw new NotFoundException("Customer is not found!");
        }
        Cart cartModel = new Cart();
        List<com.thinkpalm.thinkfood.model.CartItem> cartItemModelList = new ArrayList<>();
        List<com.thinkpalm.thinkfood.entity.Cart> cartOfSameCustomer = cartRepository.findByCustomer_IdAndIsActiveTrue(customerId);
        for(com.thinkpalm.thinkfood.entity.Cart sameCart : cartOfSameCustomer){
            if(sameCart.getIsActive()){

                cartModel.setCart(sameCart.getId());
                cartModel.setCustomer(sameCart.getCustomer().getId());
                cartModel.setCartItem(cartModel.getCartItem());
                cartModel.setTotalAmount(sameCart.getTotalAmount());
                List<CartItem> cartItems = sameCart.getCartItems();
                log.info("Iterating through the items in the particular cart.");

                for (CartItem cartItem : cartItems) {
                    if(cartItem.getIsActive()){
                        com.thinkpalm.thinkfood.model.CartItem cartItemList = new com.thinkpalm.thinkfood.model.CartItem();
                        cartItemList.setId(cartItem.getId());
                        cartItemList.setItemId(cartItem.getMenu().getId());
                        cartItemList.setItemName(cartItem.getMenu().getItem().getItemName());
                        cartItemList.setItemImage(cartItem.getMenu().getItem().getImage());
                        cartItemList.setItemPrice(cartItem.getItemPrice());
                        cartItemList.setSubtotal(cartItem.getSubtotal());
                        cartItemList.setQuantity(cartItem.getQuantity());
                        cartItemModelList.add(cartItemList);
                    } else {
                        log.error("CartItem not found exception is thrown.");
                        continue;
                    }

                }
            }
        }
        Collections.sort(cartItemModelList, Comparator.comparing(com.thinkpalm.thinkfood.model.CartItem::getItemName));
        cartModel.setCartItem(cartItemModelList);
        log.info("Cart with the specified Id is being return to this method in a model.");
        return cartModel;
    }

    public Cart updateCart(Cart cartRequest) throws NotFoundException, NotSameRestaurantException, NotAvailableException {
        Optional<com.thinkpalm.thinkfood.entity.Cart> optionalCart=cartRepository.findById(cartRequest.getCart());
        if(optionalCart.isEmpty()){
            log.error("Cart is not present");
            throw new NotFoundException("Cart is not found with id: "+cartRequest.getCart());
        }

        List<CartItem> existingCartItems = new ArrayList<>();

        log.info("Retrieving the existing cart items from the database");
        if (cartRequest.getCart() != null) {
            Optional<com.thinkpalm.thinkfood.entity.Cart> existingCartOptional = cartRepository.findById(cartRequest.getCart());
            if (existingCartOptional.isPresent()) {
                com.thinkpalm.thinkfood.entity.Cart existingCart = existingCartOptional.get();
                existingCartItems = existingCart.getCartItems();
                existingCartItems = existingCartItems.stream().filter(CartItem::getIsActive).collect(Collectors.toList());
            }
        }
        com.thinkpalm.thinkfood.entity.Cart existingCart=optionalCart.get();
        Optional<Customer> customerEntityWrapper = customerRepository.findById(cartRequest.getCustomer());
        if (customerEntityWrapper.isEmpty()) {
            log.error("Customer is not found exception is thrown.");
            throw new NotFoundException("Customer is not found with id: " + cartRequest.getCustomer());
        }
        Customer customer = customerEntityWrapper.get();
        if(existingCart.getCustomer()==customer){
            existingCart.setCustomer(customer);
        }
        double totalAmount = 0.0;

        log.info("Entering into the cartItems in the cartRequest.");
        for (com.thinkpalm.thinkfood.model.CartItem itemRequest : cartRequest.getCartItem()) {
            Optional<Menu> menuOptional = menuRepository.findById(itemRequest.getItemId());

            if (menuOptional.isEmpty()) {
                log.error("Menu is not found exception is thrown.");
                throw new NotFoundException("Menu is not found with id: " + itemRequest.getItemId());
            }

            Long restaurantId = menuOptional.get().getRestaurant().getId();
            log.info("Checking if the item is from the same restaurant.");
            if (isSameRestaurant(existingCartItems, restaurantId)) {
                if (menuOptional.get().getItemAvailability()) {
                    Menu menu = menuOptional.get();

                    log.info("Check if the item already exists in the cart.");
                    CartItem existingCartItem = findExistingCartItem(existingCartItems, menu.getId());

                    if (existingCartItem != null ) {
                        log.info("Updating the quantity");
                        existingCartItem.setQuantity(itemRequest.getQuantity()+existingCartItem.getQuantity());
                        existingCartItem.setSubtotal(existingCartItem.getQuantity() * menu.getItemPrice());
//                        totalAmount += existingCartItem.getSubtotal();
                    } else {
                        log.info("Creating a new cart item");
                        CartItem cartItem = new CartItem();
                        cartItem.setCart(existingCart);
                        cartItem.setMenu(menu);
                        cartItem.setQuantity(itemRequest.getQuantity());
                        cartItem.setIsActive(true);
                        cartItem.setItemPrice(menu.getItemPrice());

                        double itemTotalAmount = itemRequest.getQuantity() * menu.getItemPrice();
                        cartItem.setSubtotal(itemTotalAmount);
                        existingCartItems.add(cartItem);
//                        totalAmount += itemTotalAmount;
                    }
//                    totalAmount += itemRequest.getQuantity() * menu.getItemPrice();
                } else {
                    log.error("Item is not available exception is thrown.");
                    throw new NotAvailableException("Item is not available at the moment or Item is not Present!");
                }
            } else {
                log.error("Item is not from the same restaurant exception is thrown.");
                throw new NotSameRestaurantException("Item is not from the same restaurant!");
            }
        }
        totalAmount = existingCartItems.stream().mapToDouble(CartItem::getSubtotal).sum();
//        for (CartItem existingCartItem : existingCartItems) {
//            if (!cartRequest.getCartItem().stream().anyMatch(itemRequest ->
//                    itemRequest.getItemId().equals(existingCartItem.getMenu().getId()))) {
//                totalAmount += existingCartItem.getSubtotal();
//            }
//        }

        existingCart.setCartItems(existingCartItems);
        existingCart.setTotalAmount(totalAmount);

        log.info("Cart is getting saved to the database.");
        cartRepository.save(existingCart);
        log.info("CartItems are getting saved to the database.");
        cartItemRepository.saveAll(existingCartItems);


        for (CartItem savedCartItem : existingCartItems) {
            for (com.thinkpalm.thinkfood.model.CartItem cartItemRequest : cartRequest.getCartItem()) {
                cartItemRequest.setId(savedCartItem.getId());
                cartItemRequest.setQuantity(cartItemRequest.getQuantity());
                cartItemRequest.setItemPrice(savedCartItem.getItemPrice());
                cartItemRequest.setItemName(savedCartItem.getMenu().getItem().getItemName());
                cartItemRequest.setSubtotal(savedCartItem.getSubtotal());
            }
        }
        cartRequest.setTotalAmount(existingCart.getTotalAmount());
        log.info("CartRequest model is returned in this method.");
        return cartRequest;
    }
    /**
     * Add an item to the specified cart.
     *
     * @param cartRequest The cart with the new item to add.
     * @return The cart after adding the item.
     * @throws NotFoundException          if the cart or menu is not found.
     * @throws NotSameRestaurantException if the item is not from the same restaurant.
     * @throws NotAvailableException      if the item is not available.
     */
    @Override
    public Cart updateQuantity(Cart cartRequest) throws NotFoundException, NotSameRestaurantException, NotAvailableException {
        Optional<com.thinkpalm.thinkfood.entity.Cart> optionalCart=cartRepository.findById(cartRequest.getCart());
        if(optionalCart.isEmpty()){
            log.error("Cart is not present");
            throw new NotFoundException("Cart is not found with id: "+cartRequest.getCart());
        }

        List<CartItem> existingCartItems = new ArrayList<>();

        log.info("Retrieving the existing cart items from the database");
        if (cartRequest.getCart() != null) {
            Optional<com.thinkpalm.thinkfood.entity.Cart> existingCartOptional = cartRepository.findById(cartRequest.getCart());
            if (existingCartOptional.isPresent()) {
                com.thinkpalm.thinkfood.entity.Cart existingCart = existingCartOptional.get();
                existingCartItems = existingCart.getCartItems();
                existingCartItems = existingCartItems.stream().filter(CartItem::getIsActive).collect(Collectors.toList());

            }
        }
        com.thinkpalm.thinkfood.entity.Cart existingCart=optionalCart.get();
        Optional<Customer> customerEntityWrapper = customerRepository.findById(cartRequest.getCustomer());
        if (customerEntityWrapper.isEmpty()) {
            log.error("Customer is not found exception is thrown.");
            throw new NotFoundException("Customer is not found with id: " + cartRequest.getCustomer());
        }
        Customer customer = customerEntityWrapper.get();
        if(existingCart.getCustomer()==customer){
            existingCart.setCustomer(customer);
        }
        double totalAmount = 0.0;

        log.info("Entering into the cartItems in the cartRequest.");
        for (com.thinkpalm.thinkfood.model.CartItem itemRequest : cartRequest.getCartItem()) {
            Optional<Menu> menuOptional = menuRepository.findById(itemRequest.getItemId());

            if (menuOptional.isEmpty()) {
                log.error("Menu is not found exception is thrown.");
                throw new NotFoundException("Menu is not found with id: " + itemRequest.getItemId());
            }

            Long restaurantId = menuOptional.get().getRestaurant().getId();
            log.info("Checking if the item is from the same restaurant.");
            if (isSameRestaurant(existingCartItems, restaurantId)) {
                if (menuOptional.get().getItemAvailability()) {
                    Menu menu = menuOptional.get();

                    log.info("Check if the item already exists in the cart.");
                    CartItem existingCartItem = findExistingCartItem(existingCartItems, menu.getId());

                    if (existingCartItem != null) {
                        log.info("Updating the quantity");
                        existingCartItem.setQuantity(itemRequest.getQuantity());
                        existingCartItem.setSubtotal(existingCartItem.getQuantity() * menu.getItemPrice());
                        totalAmount += existingCartItem.getSubtotal();
                    } else {
                        log.info("Creating a new cart item");
                        CartItem cartItem = new CartItem();
                        cartItem.setCart(existingCart);
                        cartItem.setMenu(menu);
                        cartItem.setQuantity(itemRequest.getQuantity());
                        cartItem.setIsActive(true);
                        cartItem.setItemPrice(menu.getItemPrice());

                        double itemTotalAmount = itemRequest.getQuantity() * menu.getItemPrice();
                        cartItem.setSubtotal(itemTotalAmount);
                        existingCartItems.add(cartItem);
//                        totalAmount += itemTotalAmount;
                    }
//                    totalAmount += itemRequest.getQuantity() * menu.getItemPrice();
                } else {
                    log.error("Item is not available exception is thrown.");
                    throw new NotAvailableException("Item is not available at the moment or Item is not Present!");
                }
            } else {
                log.error("Item is not from the same restaurant exception is thrown.");
                throw new NotSameRestaurantException("Item is not from the same restaurant!");
            }
        }
        totalAmount = existingCartItems.stream().mapToDouble(CartItem::getSubtotal).sum();
//        for (CartItem existingCartItem : existingCartItems) {
//            if (!cartRequest.getCartItem().stream().anyMatch(itemRequest ->
//                    itemRequest.getItemId().equals(existingCartItem.getMenu().getId()))) {
//                totalAmount += existingCartItem.getSubtotal();
//            }
//        }

        existingCart.setCartItems(existingCartItems);
        existingCart.setTotalAmount(totalAmount);

        log.info("Cart is getting saved to the database.");
        cartRepository.save(existingCart);
        log.info("CartItems are getting saved to the database.");
        cartItemRepository.saveAll(existingCartItems);


            for (CartItem savedCartItem : existingCartItems) {
                for (com.thinkpalm.thinkfood.model.CartItem cartItemRequest : cartRequest.getCartItem()) {
                    cartItemRequest.setId(savedCartItem.getId());
                    cartItemRequest.setQuantity(cartItemRequest.getQuantity());
                    cartItemRequest.setItemPrice(savedCartItem.getItemPrice());
                    cartItemRequest.setItemName(savedCartItem.getMenu().getItem().getItemName());
                    cartItemRequest.setSubtotal(savedCartItem.getSubtotal());
                }
            }
        cartRequest.setTotalAmount(existingCart.getTotalAmount());
        log.info("CartRequest model is returned in this method.");
        return cartRequest;
    }

    private boolean isSameRestaurant(List<CartItem> cartItems, Long restaurantId) {
        if (cartItems.isEmpty()) {
            return true;
        }

        Long firstItemRestaurantId = cartItems.get(0).getMenu().getRestaurant().getId();
        return firstItemRestaurantId.equals(restaurantId);
    }

    private CartItem findExistingCartItem(List<CartItem> cartItems, Long menuId) {
        for (CartItem existingCartItem : cartItems) {
            if (existingCartItem.getMenu().getId().equals(menuId)) {
                return existingCartItem;
            }
        }
        return null;
    }



    /**
     * Delete an item from the specified cart.
     *
     * @param cartId The unique identifier of the cart.
     * @param id     The unique identifier of the item to delete.
     * @return A message indicating the success of the operation.
     */
    @Override
    public String deleteItemCart(Long cartId,Long id) {
        log.info("Fetching the details of cart of this particular Id.");
        Optional<com.thinkpalm.thinkfood.entity.Cart> optionalCart=cartRepository.findById(cartId);
        log.info("Fetching the details of cart item of particular Id.");
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);
        com.thinkpalm.thinkfood.model.CartItem response = new com.thinkpalm.thinkfood.model.CartItem();
        log.info("Checking if the cart item is present in the cart of given Id.");
        if (optionalCartItem.get().getCart().getId()==optionalCart.get().getId()){
            CartItem cartItemEntity = optionalCartItem.get();
            com.thinkpalm.thinkfood.entity.Cart cartAfterDeleted = optionalCart.get();
            Double totalAmount = cartItemEntity.getCart().getTotalAmount();
            cartItemRepository.delete(cartItemEntity);
            response.setId((cartItemEntity.getId()));
            cartAfterDeleted.setTotalAmount(totalAmount-cartItemEntity.getSubtotal());
            Double subtotal= cartItemEntity.getSubtotal()-cartItemEntity.getItemPrice();
            response.setSubtotal(subtotal);
            log.info("The cartItem is deleted successfully. ");
            return "CartItem deleted successfully";
        } else {
            response.setId(id);
            log.info("CartItem is not present in the particular cart.");
            return "CartItem is not found or it does not belong to the specified cart "+cartId;
        }
    }
    /**
     * Delete a cart by its unique identifier.
     *
     * @param id The unique identifier of the cart to delete.
     * @return A message indicating the success of the operation.
     */
    @Override
    public Cart deleteCart(Long id) {
        Optional<com.thinkpalm.thinkfood.entity.Cart> cartOptional = cartRepository.findById(id);
        if(cartOptional.isEmpty())
        {
            throw new NotFoundException("No Such Cart");
        }
        if (!cartOptional.get().getIsActive()) {
            throw new NotFoundException("Invalid cart id");
        }
        com.thinkpalm.thinkfood.entity.Cart deletedCart = cartOptional.get();

        com.thinkpalm.thinkfood.model.Cart deletedCartModel = new com.thinkpalm.thinkfood.model.Cart();
        deletedCartModel.setCart(deletedCart.getId());

        cartRepository.deleteById(id);

        return deletedCartModel;
    }
    /**
     * Soft deletes a cart with the specified ID by marking it as inactive.
     *
     * @param id The ID of the cart to be soft deleted.
     * @return A message indicating the result of the soft delete operation.
     * @throws NotFoundException If the specified cart is not found.
     */
    @Override
    public String softDeleteCart(Long id) throws NotFoundException{
        log.error("Checking if the cart of the particular id is present and if it is not present exception is thrown.");
        com.thinkpalm.thinkfood.entity.Cart cart = cartRepository.findById(id).orElseThrow(()-> new NotFoundException("Cart with ID "+ id +" not found"));
        cart.setIsActive(false);
        for(CartItem cartItem: cart.getCartItems()){
            cartItem.setIsActive(false);
        }
        cartRepository.save(cart);
        log.info("Cart is deleted successfully.");
        return "Cart with Id "+id+" deleted successfully!";
    }
    /**
     * Soft deletes a cart item with the specified ID associated with a cart by marking it as inactive.
     *
     * @param cartId The ID of the cart.
     * @param id The ID of the cart item to be soft deleted.
     * @return A message indicating the result of the soft delete operation.
     * @throws NotFoundException If the specified cart or cart item is not found.
     */
    @Override
    public String softDeleteCartItem(Long cartId, Long id) throws NotFoundException {
        log.error("Checking if the cart of the particular id is present and if it is not present exception is thrown.");
        com.thinkpalm.thinkfood.entity.Cart cart= cartRepository.findById(cartId).orElseThrow(()-> new NotFoundException("Cart with ID "+ cartId +" not found"));
        log.error("Checking if the cart of the particular id is present and if it is not present exception is thrown.");
        CartItem cartItem=cartItemRepository.findById(id).orElseThrow(()-> new NotFoundException("CartItem with ID "+id+" not found!"));
        if (cartItem.getCart().getId() == cart.getId()){
            Double totalAmount = cart.getTotalAmount();
            cartItem.setIsActive(false);
            cart.setTotalAmount(totalAmount-cartItem.getSubtotal());
            cartItemRepository.save(cartItem);
            cartRepository.save(cart);
            log.info("CartItem is deleted successfully.");
            return "CartItem with Id "+id+" present in Cart "+cartId+" is deleted successfully!";
        } else {
            log.info("CartItem is not present.");
            return "CartItem with Id "+id+" is not present in the Cart "+cartId+".";
        }

    }
}
