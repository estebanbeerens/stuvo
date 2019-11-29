import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CoreHeaderComponent} from './core-header/core-header.component';
import {CoreSideMenuComponent} from './core-side-menu/core-side-menu.component';
import {LayoutModule} from '@angular/cdk/layout';


import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatFormFieldModule} from '@angular/material/form-field';
import {
  MatCardModule,
  MatCheckboxModule,
  MatDividerModule, MatGridListModule,
  MatInputModule,
  MatSelectModule,
  MatTooltipModule,
  MatTreeModule
} from '@angular/material';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {MatMenuModule} from '@angular/material/menu';
import { CoreSpinnerComponent } from './core-spinner/core-spinner.component';
import { TodoComponent } from './todo/todo.component';

@NgModule({
  declarations: [
    AppComponent,
    CoreHeaderComponent,
    CoreSideMenuComponent,
    CoreSpinnerComponent,
    TodoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatFormFieldModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    FormsModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatTooltipModule,
    MatMenuModule,
    MatCheckboxModule,
    MatSelectModule,
    MatCardModule,
    MatDividerModule,
    MatTreeModule,
    MatGridListModule
  ],
  exports: [
    MatToolbarModule,
    MatAutocompleteModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
