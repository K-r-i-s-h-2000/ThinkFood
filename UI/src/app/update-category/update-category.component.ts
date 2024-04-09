import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoryService } from '../service/category.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-update-category',
  standalone: true,
  imports: [CommonModule, MatDialogModule, ReactiveFormsModule],
  templateUrl: './update-category.component.html',
  styleUrl: './update-category.component.scss'
})
export class UpdateCategoryComponent {
  updateForm: FormGroup;

  constructor( 
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<UpdateCategoryComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private categoryService: CategoryService
  ) {
    this.updateForm = this.fb.group({
      categoryName: [null, Validators.required],
      image: [null, Validators.required]
    });

    // If you passed categoryId in the dialog data, you can fetch the category details here
    if (data && data.categoryId) {
      this.categoryService.getCategoryById(data.categoryId).subscribe(
        (category: any) => {
          // Populate the form with the existing category details
          this.updateForm.patchValue({
            categoryName: category.categoryName,
            image: category.image
          });
        },
        (error) => {
          console.error('Error fetching category details', error);
        }
      );
    }
  }

  updateCategory() {
    if (this.updateForm.valid) {
      const updatedData = this.updateForm.value;
      // You may also include the category ID in the request payload if needed
      this.categoryService.updateCategory(this.data.categoryId, updatedData).subscribe(
        (response: any) => {
          console.log('Category updated successfully', response);
          this.openSnackBar('Category updated');
          // alert('Category updated');
          this.dialogRef.close(); // Close the dialog after successful update
        },
        (error) => {
          this.openSnackBar('Already existing category name.');
          console.error('Error updating category', error);
        }
      );
    } else {
      // alert('Form is not valid');
      this.openSnackBar('Form is not valid');
    }
  }

  closeDialog() {
    this.dialogRef.close(); // Close the dialog without updating
  }

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000, 
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
}