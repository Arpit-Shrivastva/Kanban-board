import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { BOARDS } from 'src/Models/Boards';
import { COLUMN } from 'src/Models/Column';
import { BoardService } from 'src/Services/board.service';
import { EmailvalueService } from 'src/Services/emailvalue.service';
import { LoginauthentiationService } from 'src/Services/loginauthentiation.service';
import { BoardformComponent } from 'src/boardform/boardform.component';
import { ErromsgComponent } from 'src/erromsg/erromsg.component';
import { TeammembersComponent } from 'src/teammembers/teammembers.component';
import { UpdatingboardformComponent } from 'src/updatingboardform/updatingboardform.component';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-kanban-board',
  templateUrl: './kanban-board.component.html',
  styleUrls: ['./kanban-board.component.css']
})
export class KanbanBoardComponent implements OnInit {
  constructor(
    private boardservice: BoardService,
    private dialog: MatDialog,
    private login: LoginauthentiationService,
    private route: Router,
    private emailservice: EmailvalueService,
    private snackBar: MatSnackBar
  ) {
    this.Adminemail = this.emailservice.getemail();
    if (this.Adminemail === "arpit@gmail.com") {
      this.booleanvalue = true;
    }
  }

  Adminemail: any = ""

  booleanvalue: boolean = false;


  ngOnInit() {
    this.boardservice.getboardnames().subscribe(data => {
      this.boardnames = data;
    })
  }

  boardnames: string[] = [];
  team_member: string = ""

  addteam_members(boardname: string) {
    const dialogRef = this.dialog.open(TeammembersComponent);
    dialogRef.componentInstance.searching.subscribe(data => {
      console.log(data);
      this.team_member = data;
      this.login.gettinguseremail().subscribe(data => {
        const filtered = data.filter(p => p === this.team_member);
        if (filtered.length > 0) {
          this.boardservice.addteam_members(boardname, filtered[0]).subscribe(data => {
            console.log("added team members");
            window.location.reload();
          });
        } else {
          const error_message = "Sorry, the team member is not a registered user. Please register them.";
          this.snackBar.open(error_message, 'OK', {
            duration: 5000,
          });
        }
      });
    });
  }


  addboard() {
    const dialogRef = this.dialog.open(BoardformComponent);
    dialogRef.afterClosed().subscribe(() => {
      console.log("Board added");
      this.snackBar.open('Board added successfully', 'OK', {
        duration: 3000,
      });
      window.location.reload();
    });
  }


  updateboards(boardname: string) {
    this.boardservice.getallboards().subscribe(data => {
      const filtereddata = data.filter(p => p.boardname === boardname);
      if (filtereddata.length > 0) {
        const dialogRef = this.dialog.open(UpdatingboardformComponent);
        dialogRef.componentInstance.boardform.subscribe(data => {
          console.log(data);
          this.boardservice.updatingboard(boardname, data).subscribe(updatedData => {
            console.log("successfully updated");

            this.snackBar.open('Board updated successfully', 'OK', {
              duration: 3000,
            });

            window.location.reload();
          });
        });
      }
    });
  }

}
