import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {InterfaceService} from '../interface/interface.service';
import { HostListener } from '@angular/core';
import {MatDrawer} from '@angular/material';

@Component({
  selector: 'app-core-side-menu',
  templateUrl: './core-side-menu.component.html',
  styleUrls: ['./core-side-menu.component.css']
})

export class CoreSideMenuComponent implements AfterViewInit, OnInit {
  private sideMenuToggled: boolean;
  public screenWidth: number;
  @ViewChild(MatDrawer, {static: true}) public drawer: MatDrawer;

  constructor(
    private breakpointObserver: BreakpointObserver,
    private interfaceService: InterfaceService
  ) {
  }

  ngAfterViewInit() {
    this.getScreenSize();
  }

  ngOnInit() {
    this.subscribeSideMenuToggleStatusStream();
  }

  subscribeSideMenuToggleStatusStream() {
    this.interfaceService.sideMenuToggledStatusStream.subscribe(
      sideMenuToggled => {
        this.sideMenuToggled = sideMenuToggled;
      }
    );
  }

  onBackdropClick() {
    this.interfaceService.toggleSideMenu();
  }

  @HostListener('window:resize')
  getScreenSize() {
    this.screenWidth = window.innerWidth;
    if (this.screenWidth <= 768) {
      this.drawer.mode = 'over';
      this.sideMenuToggled = false;
    } else {
      this.drawer.mode = 'side';
      this.sideMenuToggled = true;
    }
  }
}
