import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogClose } from '@angular/material/dialog';

@Component({
  selector: 'app-warning-no-agent',
  standalone: true,
  imports: [CommonModule,MatDialogClose],
  templateUrl: './warning-no-agent.component.html',
  styleUrl: './warning-no-agent.component.scss'
})
export class WarningNoAgentComponent {

}
