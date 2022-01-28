import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageSendingDialogComponent } from './message-sending-dialog.component';

describe('MessageSendingDialogComponent', () => {
  let component: MessageSendingDialogComponent;
  let fixture: ComponentFixture<MessageSendingDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MessageSendingDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MessageSendingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
