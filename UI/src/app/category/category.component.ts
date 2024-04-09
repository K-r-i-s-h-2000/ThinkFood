import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Navbar4Component } from '../components/navbar4/navbar4.component';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoryService } from '../service/category.service';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { UpdateCategoryComponent } from '../update-category/update-category.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [CommonModule, Navbar4Component, ReactiveFormsModule, RouterLink, RouterOutlet, UpdateCategoryComponent],
  templateUrl: './category.component.html',
  styleUrl: './category.component.scss'
})
export class CategoryComponent implements OnInit {
  categoryForm: FormGroup;
  // editCategoryForm: FormGroup;
  categories: any[] = [];
  // showEditForm = false;
  // editCategoryId: number;


  constructor(private fb: FormBuilder, private categoryService: CategoryService,  private dialog: MatDialog, private snackBar: MatSnackBar) {}

  ngOnInit() {
    this.categoryForm = this.fb.group({
      categoryName: [null, Validators.required],
      image: [null, Validators.required]
    });

    // this.editCategoryForm = this.fb.group({
    //   categoryName: [null, Validators.required],
    //   image: [null, Validators.required]
    // });

    this.getAllCategories();
  }

  createCategory() {
    if (this.categoryForm.valid) {
      const categoryData = this.categoryForm.value;
      this.categoryService.createCategory(categoryData).subscribe(
        (response: any) => {
          console.log('Category created successfully', response);
          this.openSnackBar('Category created successfully');
          // alert('Category saved');
          this.getAllCategories();
          // Reset the form after submission
          this.categoryForm.reset();

        },
        (error) => {
          this.openSnackBar('category already exists')
          console.log('check if this category already existing or not')
          console.error('Error creating category', error);

        }
      );
    }else{
      this.openSnackBar('Form is not valid');
      // alert('form is not valid');
    }
  }

  getAllCategories() {
    this.categoryService.getAllCategory().subscribe(
      (categories: any[]) => {
        console.log('Categories fetched successfully', categories);
        this.categories = categories;
      },
      (error) => {
        console.error('Error fetching categories', error);
      }
    );
  }

  deleteCategory(categoryId: number) {
    if(confirm('Are you sure you want to delete?')){
    this.categoryService.deleteCategory(categoryId).subscribe(
      () => {
        console.log('Category deleted successfully');
        this.openSnackBar('Category deleted successfully');
        // alert('Category deleted');
        this.getAllCategories();
      },
      (error) => {
        console.error('Error deleting category', error);
      }
    );
  }
  }

  openUpdateCategoryDialog(categoryId: number) {
    const dialogRef = this.dialog.open(UpdateCategoryComponent, {
      data: { categoryId: categoryId } // You can pass data to the dialog if needed
    });

    dialogRef.afterClosed().subscribe(result => {
      // Handle the result after the dialog is closed
      console.log('The update category dialog was closed');
      this.getAllCategories();

      // You can perform actions based on the result if needed
    });
  }

  // editCategory(category) {
  //   this.editCategoryId = category.id;
  //   this.categoryService.getCategoryById(this.editCategoryId).subscribe((result) => {
  //     this.editCategoryForm.controls['categoryName'].setValue(result.categoryName);
  //     this.editCategoryForm.controls['image'].setValue(result.image);
  //     this.showEditForm = true;
  //   });
  // }

  // updateCategory() {
  //   if (this.editCategoryForm.valid) {
  //     const updatedCategory = this.editCategoryForm.value;
  //     this.categoryService.updateCategory(this.editCategoryId, updatedCategory).subscribe(
  //       (result) => {
  //         console.log('Category updated successfully:', result);
  //         alert('Category updated successfully');
  //         this.showEditForm = false;
  //         this.getAllCategories();
  //       },
  //       (error) => {
  //         console.error('Error updating category', error);
  //       }
  //     );
  //   }else{
  //     alert('Please enter both fields');
  //   }
  // }

  // cancelEdit() {
  //   this.showEditForm = false;
  //   this.editCategoryForm.reset();
  // }

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000, 
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
}
