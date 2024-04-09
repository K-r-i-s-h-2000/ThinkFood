import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Navbar4Component } from '../components/navbar4/navbar4.component';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoryService } from '../service/category.service';
import { ItemService } from '../service/item.service';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { UpdateItemComponent } from '../update-item/update-item.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-item',
  standalone: true,
  imports: [CommonModule, Navbar4Component, ReactiveFormsModule, RouterLink, RouterOutlet, UpdateItemComponent],
  templateUrl: './item.component.html',
  styleUrl: './item.component.scss'
})

export class ItemComponent implements OnInit{
  itemForm: FormGroup;
  categories: any[];
  selectedCategory: any;
  items: any[];
  itemId: any;
  // editingItem: any;
  // editingForm: FormGroup;


  constructor(private formBuilder: FormBuilder, private categoryService: CategoryService, private itemService: ItemService, private dialog: MatDialog, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.createForm();
    this.loadCategories();
    // this.createEditingForm();
  }

  createForm(): void {
    this.itemForm = this.formBuilder.group({
      itemName: [null, Validators.required],
      categoryId: [null, Validators.required],
      image: [null, Validators.required],
    });
  }

  loadCategories(): void {
    this.categoryService.getAllCategory().subscribe((categories) => {
      this.categories = categories;
    });
  }

  loadItemsByCategory(categoryId: number): void {
    this.selectedCategory = this.categories.find(category => category.id === categoryId);
    this.itemService.getAllItemsByCategory(categoryId).subscribe((items) => {
      this.items = items;
    });
  }


  createItem(): void {
    if (this.itemForm.valid) {
      const itemData = {
        itemName: this.itemForm.value.itemName,
        image: this.itemForm.value.image,
      };

      const categoryId = this.itemForm.value.categoryId;

      this.itemService.createItem(itemData, categoryId).subscribe(
        (result) => {
          console.log('Item created successfully', result);
          this.openSnackBar('Item created successfully');
          // alert('Item saved successfully');
          this.loadItemsByCategory(categoryId);

        },
        (error) => {
          this.openSnackBar('Item already exists');
          console.error('Error creating item', error);

        }
      );
    }else{
      // alert('Form is not valid');
      this.openSnackBar('Form is not valid');
    }
  }

  deleteItem(itemId: any) {

    if (confirm('Are you sure you want to delete?')) {
    this.itemService.softDeleteItem(itemId).subscribe(
      (response) => {
        console.log('Item soft-deleted successfully:', response);
        this.openSnackBar('Item deleted successfully');
        // alert('Item deleted successfully');
        this.loadItemsByCategory(this.selectedCategory.id);
      },
      (error) => {
        console.error('Error soft-deleting item', error);
      }
    );
    }
  }

  openUpdateItemDialog(item: any) {
    const dialogRef = this.dialog.open(UpdateItemComponent, {
      data: { item: item}
    });

    dialogRef.afterClosed().subscribe(result => {
      // Handle the result after the dialog is closed
      console.log('The update item dialog was closed');
      this.loadItemsByCategory(this.selectedCategory.id);

      // You can perform actions based on the result if needed
    });
  }


  // createEditingForm(): void {
  //   this.editingForm = this.formBuilder.group({
  //     itemName: [null, Validators.required],
  //     image: [null, Validators.required],
  //   });
  // }

  // editItem(item: any): void {
  //   this.editingItem = item;
  //   this.editingForm.setValue({
  //     itemName: item.itemName,
  //     image: item.image,
  //   });
  // }

  // updateItem(): void {
  //   if (this.editingForm.valid && this.editingItem) {
  //     const itemId = this.editingItem.id;
  //     const itemData = {
  //       itemName: this.editingForm.value.itemName,
  //       image: this.editingForm.value.image,
  //     };

  //     this.itemService.updateItem(itemData, itemId).subscribe(
  //       (result) => {
  //         console.log('Item updated successfully', result);
  //         alert('Item updated successfully');
  //         this.loadItemsByCategory(this.selectedCategory.id);
  //         this.editingForm.reset();
  //         this.editingItem = null;
  //       },
  //       (error) => {
  //         console.error('Error updating item', error);
  //       }
  //     );
  //   }else{
  //     alert('enter all fields');
  //   }
  // }

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000, 
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
}


