import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { HomeComponent } from './home/home.component';
import { DemolandingComponent } from './Dashboard/demolanding/demolanding.component';
import { AuthGuard } from './shared/auth.guard';
import { OrderComponent } from './order/order.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { AllOrdersComponent } from './order/all-orders/all-orders.component';
import { CartComponent } from './cart/cart.component';
// import { RestaurantpageComponent } from './restaurantpage/restaurantpage.component';
import { FaqComponent } from './faq/faq.component';
import { AdminComponent } from './admin/admin.component';
import { ChangepasswordComponent } from './changepassword/changepassword.component';
import { EditprofileComponent } from './editprofile/editprofile.component';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { DeliveryHomeComponent } from './delivery-agent/delivery-home/delivery-home.component';
import { DeliveryHistoryComponent } from './delivery-agent/delivery-history/delivery-history.component';
import { DeliveryCurrentComponent } from './delivery-agent/delivery-current/delivery-current.component';
import { TocartComponent } from './tocart/tocart.component';
import { ListitemsComponent } from './listitems/listitems.component';
import { ListcategoriesComponent } from './Dashboard/demolanding/categorylisting/listcategories/listcategories.component';
import { ListrestaurantsComponent } from './Dashboard/demolanding/restaurant listing/listrestaurants/listrestaurants.component';
import { ViewcustomerdetailsComponent } from './viewcustomerdetails/viewcustomerdetails.component';
// import { PaymentComponent } from './payment/payment.component';
import { CouponComponent } from './coupon/coupon.component';
import { RestaurantpageComponent } from './restaurantpage/restaurantpage.component';
import { PaymentComponent } from './payment/payment.component';
import { UpdateDeliveryComponent } from './delivery-agent/update-delivery/update-delivery.component';
import { ItemComponent } from './item/item.component';
import { CategoryComponent } from './category/category.component';
import { ListFaqComponent } from './list-faq/list-faq.component';
import { ListAllCouponComponent } from './coupon/list-all-coupon/list-all-coupon.component';
import { DeliveryDetailsComponent } from './delivery-details/delivery-details.component';
import { UpdateCouponComponent } from './coupon/update-coupon/update-coupon.component';


import { ReviewsAndRatingsComponent } from './reviews-and-ratings/reviews-and-ratings.component';
import { UpdateReviewsComponent } from './update-reviews/update-reviews.component';
import { ListItemRestaurantsComponent } from './list-item-restaurants/list-item-restaurants.component';
import { OrderDetailsComponent } from './order/order-details/order-details.component';
import { restaurantAuthGuardGuard } from './restaurant-auth-guard.guard';
import { TermsComponent } from './terms/terms.component';
import { PrivacyComponent } from './privacy/privacy.component';
import { AdminAuthGuard } from './adminguard.guard';

export const routes: Routes = [
    {path:"Login", component:LoginComponent},
    {path:"Sign-up", component:SignupComponent},
    {path:"Home", component:HomeComponent},
    {path :'', component:HomeComponent},
    {path :"Order", component:OrderComponent,canActivate:[AuthGuard]},
    {path:"Sidebar",component:SidebarComponent,canActivate:[AuthGuard]},
    {path:"All-order",component:AllOrdersComponent,canActivate:[AuthGuard] },
    {path:"Cart",component:CartComponent,canActivate: [AuthGuard]},
    {path:"Restaurant",component:RestaurantpageComponent,canActivate:[restaurantAuthGuardGuard]},
    {path:"Cpass", component:ChangepasswordComponent,canActivate: [AuthGuard]},
    {path:"ForgotPassword", component:ForgotpasswordComponent},
    {path:"EditProfile", component:EditprofileComponent,canActivate:[ AuthGuard]},
    {path:"Payment",component:PaymentComponent,canActivate:[ AuthGuard]},
    {path:"Coupon",component:CouponComponent},
    {path:"Update-coupon",component:UpdateCouponComponent},

    { path: 'ToCart/:id', component: TocartComponent ,canActivate:[ AuthGuard]},
    { path: `order-details/:id`, component: OrderDetailsComponent ,canActivate:[ AuthGuard]},
    {path:"ListItems/:id", component:ListitemsComponent,canActivate:[ AuthGuard]},
    {path:"ListCategories", component:ListcategoriesComponent,canActivate:[ AuthGuard]},
    {path:"ListRestaurants", component:ListrestaurantsComponent,canActivate:[ AuthGuard]},
    {path:"ViewDetails", component:ViewcustomerdetailsComponent,canActivate:[ AuthGuard]},
    {path:"ListItemRestaurant", component:ListItemRestaurantsComponent},



    {path:"Demo",component:DemolandingComponent,canActivate:[ AuthGuard]},
    {path:"faq", component:FaqComponent,canActivate:[ AdminAuthGuard]},
    {path:"admin", component:AdminComponent,canActivate:[ AdminAuthGuard]},
    {path: "item", component:ItemComponent,canActivate:[ AdminAuthGuard]},
    {path: "category", component:CategoryComponent,canActivate:[ AdminAuthGuard]},
    {path:"list-faq", component:ListFaqComponent,canActivate:[ AuthGuard]},
    {path: 'reviews', component:ReviewsAndRatingsComponent,canActivate:[ AuthGuard] },
    // {path: 'update-reviews/:id', component: UpdateReviewsComponent, canActivate:[ AuthGuard] },

    { path:"deliveryhome",component:DeliveryHomeComponent},
    {path:"deliveryHistory", component:DeliveryHistoryComponent},
    {path:"deliveryCurrent", component:DeliveryCurrentComponent},
    {path:"updateDelivery", component:UpdateDeliveryComponent},
    {path:"deliveryDetails", component:DeliveryDetailsComponent,canActivate:[ AuthGuard]},
    {path:"list-all-coupon", component:ListAllCouponComponent},

    {path:"deliveryCurrent", component:DeliveryCurrentComponent},
    {path:"terms", component:TermsComponent},
    {path:"privacy", component:PrivacyComponent}
];
