
package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.exception.CustomerNotFoundException;
import com.thinkpalm.thinkfood.exception.NotAvailableException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.exception.NotSameRestaurantException;
import com.thinkpalm.thinkfood.model.Cart;
import com.thinkpalm.thinkfood.model.Menu;
import com.thinkpalm.thinkfood.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * REST controller class for managing cart-related operations.
 * @author krishna.c
 * @version 31/10/2023
 * @since 31/10/2023
 */
@Log4j2
@RestController
@RequestMapping("/think-food")
@SecurityRequirement(name="thinkfood")
public class CartController {
   /**
    * Autowired instance of CartService to handle cart-related business logic.
    */
   @Autowired
   private CartService cartService;
   /**
    * Endpoint to create a new cart.
    *
    * @param cart The Cart object containing cart information.
    * @return ResponseEntity with a success message if the cart is created successfully,
    *         or an error message if there are issues during cart creation.
    */
   @PostMapping("/cart")
   public Cart createAndUpdateCart(@RequestBody Cart cart){
      log.info("If the cart is present, then updating cart otherwise creating cart.");
      return cartService.createAndUpdateCart(cart);
   }
   @PostMapping("/carts")
   public Cart createCart(@RequestBody Cart cart) {
      try{
         log.info("Entered into create cart successfully.");
         return cartService.createCart(cart);
      }
//      catch(NotAvailableException e){
//         log.error("Item not available exception is thrown");
//         return ResponseEntity.badRequest().body("Item is not available at the moment!");
//      } catch (NotSameRestaurantException e) {
//         log.error("Item is not from the same restaurant exception is thrown.");
//          return ResponseEntity.badRequest().body("Item is not from the same restaurant!");
      catch (NotFoundException e){
         log.error("Customer not found exception is thrown.");
         return null;
      }
   }
   /**
    * Endpoint to find a cart by its ID.
    *
    * @param cartId The ID of the cart to be retrieved.
    * @return Cart object representing the cart with the given ID.
    * @throws NotFoundException If the specified cart is not found.
    */
   @GetMapping("/cart")
   public ResponseEntity<Object> findCartById(@RequestParam Long cartId) throws NotFoundException {
      try{
         log.info("Finding cart by ID: "+cartId);
         Cart cart = cartService.findCartById(cartId);
         return ResponseEntity.ok(cart);
      } catch (NotFoundException e){
         log.error("Cart not found exception is thrown.");
         return ResponseEntity.badRequest().body("Cart is not found!");
      }

   }
   @GetMapping("/carts")
   public Cart findCartByCustomerId(@RequestParam Long customerId) throws NotFoundException {
      try{
         log.info("Cart of particular customer of Id:"+customerId);
         return cartService.findCartByCustomerId(customerId);
      } catch (NotFoundException e) {
         log.error("Cart not found for this particular customer.");
         return null;
      }
   }
   /**
    * Endpoint to add items to a cart.
    *
    * @param cartRequest The Cart object containing the new items to be added.
    * @return ResponseEntity with a success message if the items are added successfully,
    *         or an error message if there are issues during item addition.
    */
   @PutMapping("/cart")
   public Cart updateQuantity(@RequestBody Cart cartRequest) throws NotFoundException, NotSameRestaurantException, NotAvailableException, CustomerNotFoundException{
      try{
         log.info("Updating cart with new item.");
         return cartService.updateQuantity(cartRequest);
//      } catch (NotAvailableException e){
//         log.error("Item not available exception is thrown. ");
//         return ResponseEntity.badRequest().body("Item is not available at the moment! ");
//      } catch (NotSameRestaurantException e){
//         log.error("Item is not from the same restaurant exception is thrown.");
//         return ResponseEntity.badRequest().body("Item is not from the same restaurant!");
      } catch (NotFoundException e){
         log.error("Menu not found exception is thrown.");
         return null;
      }
   }
   /**
    * Endpoint to delete an item from a cart.
    *
    * @param cartId The ID of the cart from which the item will be deleted.
    * @param id The ID of the item to be deleted.
    * @return Success message indicating the item has been deleted from the cart.
    */
   @DeleteMapping("/deleteItem/{cartId}")
   public String deleteItemCart(@PathVariable Long cartId,@RequestParam Long id){
      log.info("CartItem is being deleted of ID: "+id);
      return cartService.deleteItemCart(cartId,id);
   }
   /**
    * Endpoint to delete a cart.
    *
    * @param id The ID of the cart to be deleted.
    * @return ResponseEntity with a success message if the cart is deleted successfully,
    *         or an error message if there are issues during cart deletion.
    */

   @DeleteMapping("/deleteCart")
   public ResponseEntity<String> deleteCart(@RequestParam Long id) throws NotFoundException {
      try {
         log.info("Deleting cart with ID: "+id);
         cartService.deleteCart(id);
         return ResponseEntity.ok("Cart Deleted Successfully!");
      } catch(NotFoundException e) {
         return ResponseEntity.badRequest().body("Cart is not found with ID: "+id);
      }


   }
   /**
    * Delete a cart softly by marking it as deleted, using its unique identifier.
    *
    * @param id The unique identifier of the cart to delete.
    * @return A response entity indicating the success of the operation or an error message.
    */
   @DeleteMapping("/cart")
   public ResponseEntity<String> softDeleteCart(@RequestParam Long id){
      try{
         log.info("Soft deleting cart with ID " + id);
         cartService.softDeleteCart(id);
         return ResponseEntity.ok("Cart with ID "+id+" deleted successfully!");
      } catch (NotFoundException e){
         log.error("Cart with ID " + id + " not found.");
         return ResponseEntity.badRequest().body("Cart with ID "+id+"not found!");
      }

   }
   /**
    * Deletes a specific cart item associated with a cart.
    *
    * @param cartId The ID of the cart.
    * @param id The ID of the cart item to be deleted.
    * @return A response indicating the result of the operation.
    * @throws NotFoundException If the specified cart item is not found.
    */
   @DeleteMapping("/cartItem/{cartId}")
   public Boolean softDeleteCartItem(@PathVariable Long cartId,@RequestParam Long id) throws NotFoundException{
      try{
         log.info("Soft deleting cart item with ID " + id + " from cart with ID " + cartId);
         cartService.softDeleteCartItem(cartId,id);
         return true;
      } catch (NotFoundException e){
         log.error("CartItem with Id " + id + " is not present in the Cart " + cartId);
         return false;
      }
   }
}
