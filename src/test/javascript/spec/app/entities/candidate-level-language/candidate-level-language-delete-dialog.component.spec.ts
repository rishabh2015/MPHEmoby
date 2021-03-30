import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EmobyMphTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CandidateLevelLanguageDeleteDialogComponent } from 'app/entities/candidate-level-language/candidate-level-language-delete-dialog.component';
import { CandidateLevelLanguageService } from 'app/entities/candidate-level-language/candidate-level-language.service';

describe('Component Tests', () => {
  describe('CandidateLevelLanguage Management Delete Component', () => {
    let comp: CandidateLevelLanguageDeleteDialogComponent;
    let fixture: ComponentFixture<CandidateLevelLanguageDeleteDialogComponent>;
    let service: CandidateLevelLanguageService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [CandidateLevelLanguageDeleteDialogComponent],
      })
        .overrideTemplate(CandidateLevelLanguageDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CandidateLevelLanguageDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CandidateLevelLanguageService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});