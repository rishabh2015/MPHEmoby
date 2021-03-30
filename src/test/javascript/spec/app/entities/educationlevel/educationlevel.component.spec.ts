import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { EducationlevelComponent } from 'app/entities/educationlevel/educationlevel.component';
import { EducationlevelService } from 'app/entities/educationlevel/educationlevel.service';
import { Educationlevel } from 'app/shared/model/educationlevel.model';

describe('Component Tests', () => {
  describe('Educationlevel Management Component', () => {
    let comp: EducationlevelComponent;
    let fixture: ComponentFixture<EducationlevelComponent>;
    let service: EducationlevelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [EducationlevelComponent],
      })
        .overrideTemplate(EducationlevelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EducationlevelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EducationlevelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Educationlevel(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.educationlevels && comp.educationlevels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
