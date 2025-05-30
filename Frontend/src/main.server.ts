import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/Pages/main/app.component';
import { config } from './app/app.config.server';

const bootstrap = () => bootstrapApplication(AppComponent, config);

export default bootstrap;
