import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ItemService } from '../service/item.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-update-item',
  standalone: true,
  imports: [CommonModule, MatDialogModule, ReactiveFormsModule],
  templateUrl: './update-item.component.html',
  styleUrl: './update-item.component.scss'
})
export class UpdateItemComponent implements OnInit{
  updateItemForm: FormGroup;
  itemData: any; 

  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<UpdateItemComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { item: any },
    private fb: FormBuilder,
    private itemService: ItemService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.loadItemDetails();
  }

  createForm(): void {
    this.updateItemForm = this.fb.group({
      itemName: [null, Validators.required],
      image: [null, Validators.required],
    });
  }

  loadItemDetails(): void {
    // Extract item data from passed object
    this.itemData = this.data.item;
    // Patch the form with the item data
    this.updateItemForm.patchValue({
      itemName: this.itemData.itemName,
      image: this.itemData.image,
    });
  }

  updateItem() {
    if (this.updateItemForm.valid) {
      const updatedItemData = this.updateItemForm.value;
      const itemId = this.itemData.id;
      this.itemService.updateItem(updatedItemData,itemId).subscribe(
        (response: any) => {
          console.log('Item updated successfully', response);
          // alert('Item updated');
          this.openSnackBar('Item updated successfully');
          this.dialogRef.close(); // Close the dialog after successful update
        },
        (error) => {
          this.openSnackBar('Already existing item name.');
          console.error('Error updating item', error);
        }
      );
    } else {
      // alert('Form is not valid');
      this.openSnackBar('Form is not valid');
    }
  }

  closeDialog() {
    this.dialogRef.close(); 
  }

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000, 
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
}
