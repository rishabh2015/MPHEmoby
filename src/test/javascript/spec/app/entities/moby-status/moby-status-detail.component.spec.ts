import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { MobyStatusDetailComponent } from 'app/entities/moby-status/moby-status-detail.component';
import { MobyStatus } from 'app/shared/model/moby-status.model';

describe('Component Tests', () => {
  describe('MobyStatus Management Detail Component', () => {
    let comp: MobyStatusDetailComponent;
    let fixture: ComponentFixture<MobyStatusDetailComponent>;
    const route = ({ data: of({ mobyStatus: new MobyStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [MobyStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MobyStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MobyStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mobyStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mobyStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
