import { ChangeDetectorRef, Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderService } from '../../service/order.service';
import { RestaurantService } from '../../service/restaurant.service';
import { DataService } from '../../service/data.service';
import { MenuService } from '../../service/menu.service';
import { catchError } from 'rxjs';
import { MenudataService } from '../../service/menudata.service';
import { MatDialog} from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SharedService } from '../../service/shared.service';

@Component({
  selector: 'app-viewarea',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './viewarea.component.html',
  styleUrl: './viewarea.component.scss'
})
export class ViewareaComponent implements OnInit{

  restaurantId:number;
  orderData:any;
  orderStatusData:any;
  title="editProfileForm";
  reactiveForm:FormGroup;
  editMenuForm:FormGroup;
  _dialog:MatDialog;
  editStatus:any;
  menuStatus:any;
  errorMessage: string;
  profileData: any;
  restaurantMenu: any=[];
  restaurantName:string;
  menuId:any;
  orderId:any;
  itemId:any;
  editMenuFormVisible: boolean = false;
  viewMenuVisible:boolean=false;
  createMenuForm:FormGroup;
  showHiDiv: boolean = true;
  order:any;
  itemData:any;
  viewContent:string
  showAddForm:boolean= false;
  loading: boolean = false;
  loadingOrders: { [key: string]: boolean } = {};
  filteredOrders: any[] = [];


  menuData:any;
  preparationButtonClicked = false;
  deliveryButtonClicked = false;
  selectedMenu: any = null;
  filterText: string = '';



  constructor(
    private snackBar: MatSnackBar,
    private formBuilder: FormBuilder,
    public dialog: MatDialog,
    private http:HttpClient,
    private router:Router,
    private route: ActivatedRoute,
    private restaurantService:RestaurantService,
    private dataService:DataService,
    private menuDataService:MenudataService,
    private menuService:MenuService,
    private orderService:OrderService,
    private sharedService:SharedService,
    private cdr: ChangeDetectorRef){
    this._dialog=dialog;

    }

  ngOnInit(){

    this.orderService.getOrdersByRestaurant().subscribe((data:any)=>{

      this.order = data;
      console.log(this.order);
    },
    (error)=>{
      console.error(error);
    }
    );

    this.menuDataService.notifyObservale$.subscribe((data) => {

      this.menuStatus = data;


    });



    this.dataService.notifyObservale$.subscribe((data) => {

      this.editStatus = data;
    });

    this.createMenuForm = this.formBuilder.group({
      itemDescription:['', [Validators.required]],
      itemPrice:['', [Validators.required,Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      preparationTime:['', [Validators.required,Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      itemAvailability:['', [Validators.required]]
    })

    this.editMenuForm = this.formBuilder.group({
      itemDescription: ['',[Validators.required]],
      itemPrice: ['', [Validators.required,Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      preparationTime: ['',[Validators.required,Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      itemAvailability: ['']
    });

    this.reactiveForm = this.formBuilder.group({
      restaurantName:['', [Validators.required]],
      restaurantDescription: ['', [Validators.required]],
      email: ['', [Validators.required,Validators.email]],
      restaurantLongitude:['', [Validators.required, Validators.pattern(/^\d{1,3}\.\d{4,}$/)]],
      restaurantLatitude: ['', [Validators.required, Validators.pattern(/^\d{1,3}\.\d{4,}$/)]],
      phoneNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      restaurantAvailability:['']

    });


    this.restaurantService.listItems(this.itemId).subscribe(
      (data)=>{
        console.log(data);
        this.itemData=data;
      },
      (error) => {
        console.error(error);
      }
    );



    this.getAccount();

    this.menuService.getMenuByRestaurant().subscribe(
      (data)=>{
        console.log(data);
        this.restaurantMenu=data;
      },
      (error) => {
        console.error(error);
      }

    );



  }

  showForm(id:any){

    localStorage.setItem('itemId',id);
    localStorage.getItem('itemId');
    this.showAddForm=true;
  }



  isHiVisible(): boolean {
    return this.showHiDiv && this.menuStatus !== 1 && this.editStatus !== 5 && this.menuStatus !== 3;
  }

  getAccount(){
    this.restaurantService.viewProfile().subscribe(
      (data) => {
        console.log(data);
        this.profileData=data;
      },
      (error) => {
        console.error(error);
      }
    );
  }



  onGetMenuById(menuId:any){
    console.log('onGetMenuById clicked');
    localStorage.setItem('menuId',menuId);
    this.viewMenuVisible=true;
    this.toggleMenu(menuId);

    this.menuService.viewMenu(menuId).subscribe(
      (data) => {
        console.log(data);
        this.menuData=data;
        this.showHiDiv = false;


      },
      (error) => {
        console.error(error);
      }
    );
}

  onUpdateMenu(){
     if (this.editMenuForm.valid){
    console.log('onUpdateMenu clicked');
    this.editMenuFormVisible=false;

    const formData = this.editMenuForm.getRawValue();
    this.menuService.updateMenu(formData).pipe(
      catchError((error) => {
        throw error;
      })
    ).subscribe(
      (data) => {
        console.log(data);
        this.menuStatus=3;
        this.openSuccessSnackbar("Item updated sucessfully!");
      }
    );
    console.log(formData);
     }else{
       this.openSuccessSnackbar("Form is not valid");

     }

  }



  onCancelUpdate(){
    this.editMenuFormVisible=false;
    this.showHiDiv = true;
  }

  onOrderView(orderId:any){
    console.log('order view clicked');
  }

  onStatusChange(orderId: any) {
    console.log('status updation clicked');
    localStorage.getItem('orderId');
    this.loadingOrders[orderId] = true;

    this.restaurantService.updateOrderStatus(orderId).pipe(
      catchError((error) => {
        this.loadingOrders[orderId] = false;
        console.error(error);
        throw error;
      })
    ).subscribe(
      (data) => {
        console.log(data);
        this.orderStatusData = data;
        location.reload();
        this.loadingOrders[orderId] = false;
        this.openSuccessSnackbar("Order successfully added to Out for Delivery!");
      },
      (error) => {
        console.error(error);
        this.openSuccessSnackbar("Error!Try again");

      }
    );
  }


  onDeleteMenu(menuId:any){
    console.log('onDeleteMenu clicked');
    localStorage.setItem('menuId',menuId);

    this.menuService.deleteMenu(menuId).subscribe(
      (data) => {
        console.log(data);
        this.menuData=null;
        this.openSuccessSnackbar("Item deleted successfully from the menu!");
        window.location.reload();
      },
      (error) => {
        this.openSuccessSnackbar("Error!Try again");

        console.error(error);
      }
    );

  }

  onMenuSubmit(){
    if (this.createMenuForm.valid){
    this.menuStatus=null;
    this.menuService.addMenu(this.createMenuForm.getRawValue(),this.itemId).subscribe(
      (data)=>{
        console.log(data);
        console.log(this.createMenuForm);
        this.menuStatus=1;
        this.openSuccessSnackbar("Item added to menu successfully");

      },
      (error)=>{
        console.error('Error:', error);
        console.log(this.reactiveForm);
        this.openSuccessSnackbar("Error! Item already exist in menu");

      }
    )
    }else{
      this.openSuccessSnackbar("Form is invalid!")
    }
  }


  onSubmit1(){
    if (this.reactiveForm.valid){
    console.log("Submit clicked");
    this.editStatus=null;

    if (this.reactiveForm.valid) {

    const formData = this.reactiveForm.getRawValue();
    this.restaurantService.editProfile(formData).pipe(
      catchError((error) => {
        this.openSuccessSnackbar("Error! Failed to update profile.");
        throw error;
      })
    ).subscribe(
      (data) => {
        console.log(data);
        this.openSuccessSnackbar("Profile updated successfully!");
      }
    );
    console.log(formData);
    }}
    else{
      this.openSuccessSnackbar('Form is invalid!')
    }
  }

  filterOrders(preparationStatus: string): void {
    this.filteredOrders = this.order.filter(orderItem => orderItem.preparationStatus === preparationStatus);
    this.preparationButtonClicked = preparationStatus === 'PREPARATION';
    this.deliveryButtonClicked = preparationStatus === 'OUT_FOR_DELIVERY';
  }

  closeForm() {
    this.editStatus=null;
  }

  toggleMenu(menuId: any) {
    this.selectedMenu = this.selectedMenu === menuId ? null : menuId;
    console.log(this.selectedMenu);

  }

  closeEditForm() {
    this.editMenuFormVisible = false;
  }

  closeCreateForm(){
    this.showAddForm=false;
  }

  openSuccessSnackbar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['custom-snackbar'],
    });
  }

  onUpdateMenuButton(menuData:any){
    this.editMenuFormVisible = true;
     this.editMenuForm.setValue({
      itemDescription: menuData.itemDescription,
    itemPrice: menuData.itemPrice,
    preparationTime: menuData.preparationTime,
    itemAvailability: menuData.itemAvailability
     })
  }

 






}

