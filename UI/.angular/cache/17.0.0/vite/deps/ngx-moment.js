import {
  require_moment
} from "./chunk-KRWDD6N6.js";
import {
  ChangeDetectorRef,
  EventEmitter,
  Inject,
  InjectionToken,
  NgModule,
  NgZone,
  Optional,
  Pipe,
  setClassMetadata,
  ɵɵdefineInjector,
  ɵɵdefineNgModule,
  ɵɵdefinePipe,
  ɵɵdirectiveInject
} from "./chunk-BOKUXZSO.js";
import "./chunk-AFRS2OIU.js";
import {
  __spreadValues,
  __toESM
} from "./chunk-J5XZNU7V.js";

// node_modules/ngx-moment/fesm2020/ngx-moment.mjs
var import_moment = __toESM(require_moment(), 1);
var AddPipe = class {
  transform(value, amount, unit) {
    if (typeof amount === "undefined" || typeof amount === "number" && typeof unit === "undefined") {
      throw new Error("AddPipe: missing required arguments");
    }
    return (0, import_moment.default)(value).add(amount, unit);
  }
};
AddPipe.ɵfac = function AddPipe_Factory(t) {
  return new (t || AddPipe)();
};
AddPipe.ɵpipe = ɵɵdefinePipe({
  name: "amAdd",
  type: AddPipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(AddPipe, [{
    type: Pipe,
    args: [{
      name: "amAdd"
    }]
  }], null, null);
})();
var CalendarPipe = class _CalendarPipe {
  constructor(cdRef, ngZone) {
    this.cdRef = cdRef;
    this.ngZone = ngZone;
    _CalendarPipe.initTimer(ngZone);
    _CalendarPipe.refs++;
    this.midnightSub = _CalendarPipe.midnight.subscribe(() => {
      this.ngZone.run(() => this.cdRef.markForCheck());
    });
  }
  transform(value, ...args) {
    let formats = null;
    let referenceTime = null;
    for (let i = 0, len = args.length; i < len; i++) {
      if (args[i] !== null) {
        if (typeof args[i] === "object" && !import_moment.default.isMoment(args[i])) {
          formats = args[i];
        } else {
          referenceTime = (0, import_moment.default)(args[i]);
        }
      }
    }
    return (0, import_moment.default)(value).calendar(referenceTime, formats);
  }
  ngOnDestroy() {
    if (_CalendarPipe.refs > 0) {
      _CalendarPipe.refs--;
    }
    if (_CalendarPipe.refs === 0) {
      _CalendarPipe.removeTimer();
    }
    this.midnightSub.unsubscribe();
  }
  static initTimer(ngZone) {
    if (!_CalendarPipe.midnight) {
      _CalendarPipe.midnight = new EventEmitter();
      if (typeof window !== "undefined") {
        const timeToUpdate = _CalendarPipe._getMillisecondsUntilUpdate();
        _CalendarPipe.timer = ngZone.runOutsideAngular(() => {
          return window.setTimeout(() => {
            _CalendarPipe.midnight.emit(/* @__PURE__ */ new Date());
            _CalendarPipe.removeTimer();
            _CalendarPipe.initTimer(ngZone);
          }, timeToUpdate);
        });
      }
    }
  }
  static removeTimer() {
    if (_CalendarPipe.timer) {
      window.clearTimeout(_CalendarPipe.timer);
      _CalendarPipe.timer = null;
      _CalendarPipe.midnight = null;
    }
  }
  static _getMillisecondsUntilUpdate() {
    const now = (0, import_moment.default)();
    const tomorrow = (0, import_moment.default)().startOf("day").add(1, "days");
    const timeToMidnight = tomorrow.valueOf() - now.valueOf();
    return timeToMidnight + 1e3;
  }
};
CalendarPipe.refs = 0;
CalendarPipe.timer = null;
CalendarPipe.midnight = null;
CalendarPipe.ɵfac = function CalendarPipe_Factory(t) {
  return new (t || CalendarPipe)(ɵɵdirectiveInject(ChangeDetectorRef, 16), ɵɵdirectiveInject(NgZone, 16));
};
CalendarPipe.ɵpipe = ɵɵdefinePipe({
  name: "amCalendar",
  type: CalendarPipe,
  pure: false
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(CalendarPipe, [{
    type: Pipe,
    args: [{
      name: "amCalendar",
      pure: false
    }]
  }], function() {
    return [{
      type: ChangeDetectorRef
    }, {
      type: NgZone
    }];
  }, null);
})();
var DateFormatPipe = class {
  transform(value, ...args) {
    if (!value) {
      return "";
    }
    return (0, import_moment.default)(value).format(args[0]);
  }
};
DateFormatPipe.ɵfac = function DateFormatPipe_Factory(t) {
  return new (t || DateFormatPipe)();
};
DateFormatPipe.ɵpipe = ɵɵdefinePipe({
  name: "amDateFormat",
  type: DateFormatPipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(DateFormatPipe, [{
    type: Pipe,
    args: [{
      name: "amDateFormat"
    }]
  }], null, null);
})();
var DifferencePipe = class {
  transform(value, otherValue, unit, precision) {
    const date = (0, import_moment.default)(value);
    const date2 = otherValue !== null ? (0, import_moment.default)(otherValue) : (0, import_moment.default)();
    return date.diff(date2, unit, precision);
  }
};
DifferencePipe.ɵfac = function DifferencePipe_Factory(t) {
  return new (t || DifferencePipe)();
};
DifferencePipe.ɵpipe = ɵɵdefinePipe({
  name: "amDifference",
  type: DifferencePipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(DifferencePipe, [{
    type: Pipe,
    args: [{
      name: "amDifference"
    }]
  }], null, null);
})();
var NGX_MOMENT_OPTIONS = new InjectionToken("NGX_MOMENT_OPTIONS");
var DurationPipe = class {
  constructor(momentOptions) {
    this.allowedUnits = ["ss", "s", "m", "h", "d", "M"];
    this._applyOptions(momentOptions);
  }
  transform(value, ...args) {
    if (typeof args === "undefined" || args.length !== 1) {
      throw new Error("DurationPipe: missing required time unit argument");
    }
    return import_moment.default.duration(value, args[0]).humanize();
  }
  _applyOptions(momentOptions) {
    if (!momentOptions) {
      return;
    }
    if (!!momentOptions.relativeTimeThresholdOptions) {
      const units = Object.keys(momentOptions.relativeTimeThresholdOptions);
      const filteredUnits = units.filter((unit) => this.allowedUnits.indexOf(unit) !== -1);
      filteredUnits.forEach((unit) => {
        import_moment.default.relativeTimeThreshold(unit, momentOptions.relativeTimeThresholdOptions[unit]);
      });
    }
  }
};
DurationPipe.ɵfac = function DurationPipe_Factory(t) {
  return new (t || DurationPipe)(ɵɵdirectiveInject(NGX_MOMENT_OPTIONS, 24));
};
DurationPipe.ɵpipe = ɵɵdefinePipe({
  name: "amDuration",
  type: DurationPipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(DurationPipe, [{
    type: Pipe,
    args: [{
      name: "amDuration"
    }]
  }], function() {
    return [{
      type: void 0,
      decorators: [{
        type: Optional
      }, {
        type: Inject,
        args: [NGX_MOMENT_OPTIONS]
      }]
    }];
  }, null);
})();
var FromUnixPipe = class {
  transform(value, ...args) {
    return typeof value === "string" ? import_moment.default.unix(parseInt(value, 10)) : import_moment.default.unix(value);
  }
};
FromUnixPipe.ɵfac = function FromUnixPipe_Factory(t) {
  return new (t || FromUnixPipe)();
};
FromUnixPipe.ɵpipe = ɵɵdefinePipe({
  name: "amFromUnix",
  type: FromUnixPipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(FromUnixPipe, [{
    type: Pipe,
    args: [{
      name: "amFromUnix"
    }]
  }], null, null);
})();
var ParsePipe = class {
  transform(value, formats) {
    return (0, import_moment.default)(value, formats);
  }
};
ParsePipe.ɵfac = function ParsePipe_Factory(t) {
  return new (t || ParsePipe)();
};
ParsePipe.ɵpipe = ɵɵdefinePipe({
  name: "amParse",
  type: ParsePipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(ParsePipe, [{
    type: Pipe,
    args: [{
      name: "amParse"
    }]
  }], null, null);
})();
var FromUtcPipe = class {
  transform(value, formats, ...args) {
    return formats ? import_moment.default.utc(value, formats) : import_moment.default.utc(value);
  }
};
FromUtcPipe.ɵfac = function FromUtcPipe_Factory(t) {
  return new (t || FromUtcPipe)();
};
FromUtcPipe.ɵpipe = ɵɵdefinePipe({
  name: "amFromUtc",
  type: FromUtcPipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(FromUtcPipe, [{
    type: Pipe,
    args: [{
      name: "amFromUtc"
    }]
  }], null, null);
})();
var IsAfterPipe = class {
  transform(value, otherValue, unit) {
    return (0, import_moment.default)(value).isAfter((0, import_moment.default)(otherValue), unit);
  }
};
IsAfterPipe.ɵfac = function IsAfterPipe_Factory(t) {
  return new (t || IsAfterPipe)();
};
IsAfterPipe.ɵpipe = ɵɵdefinePipe({
  name: "amIsAfter",
  type: IsAfterPipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(IsAfterPipe, [{
    type: Pipe,
    args: [{
      name: "amIsAfter"
    }]
  }], null, null);
})();
var IsBeforePipe = class {
  transform(value, otherValue, unit) {
    return (0, import_moment.default)(value).isBefore((0, import_moment.default)(otherValue), unit);
  }
};
IsBeforePipe.ɵfac = function IsBeforePipe_Factory(t) {
  return new (t || IsBeforePipe)();
};
IsBeforePipe.ɵpipe = ɵɵdefinePipe({
  name: "amIsBefore",
  type: IsBeforePipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(IsBeforePipe, [{
    type: Pipe,
    args: [{
      name: "amIsBefore"
    }]
  }], null, null);
})();
var LocalTimePipe = class {
  transform(value) {
    return (0, import_moment.default)(value).local();
  }
};
LocalTimePipe.ɵfac = function LocalTimePipe_Factory(t) {
  return new (t || LocalTimePipe)();
};
LocalTimePipe.ɵpipe = ɵɵdefinePipe({
  name: "amLocal",
  type: LocalTimePipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(LocalTimePipe, [{
    type: Pipe,
    args: [{
      name: "amLocal"
    }]
  }], null, null);
})();
var LocalePipe = class {
  transform(value, locale) {
    return (0, import_moment.default)(value).locale(locale);
  }
};
LocalePipe.ɵfac = function LocalePipe_Factory(t) {
  return new (t || LocalePipe)();
};
LocalePipe.ɵpipe = ɵɵdefinePipe({
  name: "amLocale",
  type: LocalePipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(LocalePipe, [{
    type: Pipe,
    args: [{
      name: "amLocale"
    }]
  }], null, null);
})();
var ParseZonePipe = class {
  transform(value) {
    return import_moment.default.parseZone(value);
  }
};
ParseZonePipe.ɵfac = function ParseZonePipe_Factory(t) {
  return new (t || ParseZonePipe)();
};
ParseZonePipe.ɵpipe = ɵɵdefinePipe({
  name: "amParseZone",
  type: ParseZonePipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(ParseZonePipe, [{
    type: Pipe,
    args: [{
      name: "amParseZone"
    }]
  }], null, null);
})();
var SubtractPipe = class {
  transform(value, amount, unit) {
    if (typeof amount === "undefined" || typeof amount === "number" && typeof unit === "undefined") {
      throw new Error("SubtractPipe: missing required arguments");
    }
    return (0, import_moment.default)(value).subtract(amount, unit);
  }
};
SubtractPipe.ɵfac = function SubtractPipe_Factory(t) {
  return new (t || SubtractPipe)();
};
SubtractPipe.ɵpipe = ɵɵdefinePipe({
  name: "amSubtract",
  type: SubtractPipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(SubtractPipe, [{
    type: Pipe,
    args: [{
      name: "amSubtract"
    }]
  }], null, null);
})();
var TimeAgoPipe = class {
  constructor(cdRef, ngZone) {
    this.cdRef = cdRef;
    this.ngZone = ngZone;
  }
  format(m) {
    return m.from((0, import_moment.default)(), this.lastOmitSuffix);
  }
  transform(value, omitSuffix, formatFn) {
    if (this.hasChanged(value, omitSuffix)) {
      this.lastTime = this.getTime(value);
      this.lastValue = value;
      this.lastOmitSuffix = omitSuffix;
      this.lastLocale = this.getLocale(value);
      this.formatFn = formatFn || this.format.bind(this);
      this.removeTimer();
      this.createTimer();
      this.lastText = this.formatFn((0, import_moment.default)(value));
    } else {
      this.createTimer();
    }
    return this.lastText;
  }
  ngOnDestroy() {
    this.removeTimer();
  }
  createTimer() {
    if (this.currentTimer) {
      return;
    }
    const momentInstance = (0, import_moment.default)(this.lastValue);
    const timeToUpdate = this.getSecondsUntilUpdate(momentInstance) * 1e3;
    this.currentTimer = this.ngZone.runOutsideAngular(() => {
      if (typeof window !== "undefined") {
        return window.setTimeout(() => {
          this.lastText = this.formatFn((0, import_moment.default)(this.lastValue));
          this.currentTimer = null;
          this.ngZone.run(() => this.cdRef.markForCheck());
        }, timeToUpdate);
      } else {
        return null;
      }
    });
  }
  removeTimer() {
    if (this.currentTimer) {
      window.clearTimeout(this.currentTimer);
      this.currentTimer = null;
    }
  }
  getSecondsUntilUpdate(momentInstance) {
    const howOld = Math.abs((0, import_moment.default)().diff(momentInstance, "minute"));
    if (howOld < 1) {
      return 1;
    } else if (howOld < 60) {
      return 30;
    } else if (howOld < 180) {
      return 300;
    } else {
      return 3600;
    }
  }
  hasChanged(value, omitSuffix) {
    return this.getTime(value) !== this.lastTime || this.getLocale(value) !== this.lastLocale || omitSuffix !== this.lastOmitSuffix;
  }
  getTime(value) {
    if (import_moment.default.isDate(value)) {
      return value.getTime();
    } else if (import_moment.default.isMoment(value)) {
      return value.valueOf();
    } else {
      return (0, import_moment.default)(value).valueOf();
    }
  }
  getLocale(value) {
    return import_moment.default.isMoment(value) ? value.locale() : import_moment.default.locale();
  }
};
TimeAgoPipe.ɵfac = function TimeAgoPipe_Factory(t) {
  return new (t || TimeAgoPipe)(ɵɵdirectiveInject(ChangeDetectorRef, 16), ɵɵdirectiveInject(NgZone, 16));
};
TimeAgoPipe.ɵpipe = ɵɵdefinePipe({
  name: "amTimeAgo",
  type: TimeAgoPipe,
  pure: false
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(TimeAgoPipe, [{
    type: Pipe,
    args: [{
      name: "amTimeAgo",
      pure: false
    }]
  }], function() {
    return [{
      type: ChangeDetectorRef
    }, {
      type: NgZone
    }];
  }, null);
})();
var UtcPipe = class {
  transform(value) {
    return (0, import_moment.default)(value).utc();
  }
};
UtcPipe.ɵfac = function UtcPipe_Factory(t) {
  return new (t || UtcPipe)();
};
UtcPipe.ɵpipe = ɵɵdefinePipe({
  name: "amUtc",
  type: UtcPipe,
  pure: true
});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(UtcPipe, [{
    type: Pipe,
    args: [{
      name: "amUtc"
    }]
  }], null, null);
})();
var ANGULAR_MOMENT_PIPES = [AddPipe, CalendarPipe, DateFormatPipe, DifferencePipe, DurationPipe, FromUnixPipe, ParsePipe, SubtractPipe, TimeAgoPipe, UtcPipe, FromUtcPipe, LocalTimePipe, LocalePipe, ParseZonePipe, IsBeforePipe, IsAfterPipe];
var MomentModule = class _MomentModule {
  static forRoot(options) {
    return {
      ngModule: _MomentModule,
      providers: [{
        provide: NGX_MOMENT_OPTIONS,
        useValue: __spreadValues({}, options)
      }]
    };
  }
};
MomentModule.ɵfac = function MomentModule_Factory(t) {
  return new (t || MomentModule)();
};
MomentModule.ɵmod = ɵɵdefineNgModule({
  type: MomentModule,
  declarations: [AddPipe, CalendarPipe, DateFormatPipe, DifferencePipe, DurationPipe, FromUnixPipe, ParsePipe, SubtractPipe, TimeAgoPipe, UtcPipe, FromUtcPipe, LocalTimePipe, LocalePipe, ParseZonePipe, IsBeforePipe, IsAfterPipe],
  exports: [AddPipe, CalendarPipe, DateFormatPipe, DifferencePipe, DurationPipe, FromUnixPipe, ParsePipe, SubtractPipe, TimeAgoPipe, UtcPipe, FromUtcPipe, LocalTimePipe, LocalePipe, ParseZonePipe, IsBeforePipe, IsAfterPipe]
});
MomentModule.ɵinj = ɵɵdefineInjector({});
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(MomentModule, [{
    type: NgModule,
    args: [{
      declarations: ANGULAR_MOMENT_PIPES,
      exports: ANGULAR_MOMENT_PIPES
    }]
  }], null, null);
})();
export {
  AddPipe,
  CalendarPipe,
  DateFormatPipe,
  DifferencePipe,
  DurationPipe,
  FromUnixPipe,
  FromUtcPipe,
  IsAfterPipe,
  IsBeforePipe,
  LocalTimePipe,
  LocalePipe,
  MomentModule,
  NGX_MOMENT_OPTIONS,
  ParsePipe,
  ParseZonePipe,
  SubtractPipe,
  TimeAgoPipe,
  UtcPipe
};
//# sourceMappingURL=ngx-moment.js.map
