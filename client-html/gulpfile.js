const gulp = require('gulp');
const ser = gulp.series;
const par = gulp.parallel;
const task = gulp.task;
const sass = require('gulp-sass');
const sourcemaps = require('gulp-sourcemaps');
const del = require('del');
const notify = require('gulp-notify');
const tildeImporter = require('node-sass-tilde-importer');
const browserSync = require('browser-sync').create();
const rigger = require('gulp-rigger');

const paths = {
  dest: 'build/dest/',
  destWatch: 'build/dest/**/*',
  rigger: {
    src: ['source/rigger/*.html', '!source/rigger/_*'],
    root: 'source/rigger',
    srcWatch: 'source/rigger/**/*',
    dest: 'build/dest/',
  },
  styles: {
    src: 'source/styles/*.scss',
    srcWatch: 'source/styles/**/*',
    dest: 'build/dest/css/',
  },
  img: {
    src: ['source/img/**/*.png'],
    srcWatch: ['source/img/**/*'],
    dest: 'build/dest/img/',
  },
  copy_bootstrap_grid: {
    from: "node_modules/bootstrap/dist/css/bootstrap-grid.min.css",
    dest: 'build/dest/css/',
  },
};

task('start', (ok) => {
  console.log('asd');
  ok();
});

task('rigger', () => {
  return gulp.src(paths.rigger.src)
    .pipe(rigger())
    .pipe(gulp.dest(paths.rigger.dest))
    ;
});

task('rigger-watch', () => {
  gulp.watch(paths.rigger.srcWatch, ser('rigger'));
});

task('sass', () => {
  return gulp.src(paths.styles.src)
    .pipe(sourcemaps.init())
    .pipe(sass({
      outputStyle: 'expanded',
      importer: tildeImporter,
    }).on('error', sass.logError))
    .pipe(sourcemaps.write())
    .pipe(gulp.dest(paths.styles.dest));
});

task('sass-watch', () => {
  gulp.watch(paths.styles.srcWatch, ser('sass'))
});

task('img', () => {
  return gulp.src(paths.img.src)
    .pipe(gulp.dest(paths.img.dest))
    ;
});

task('img-watch', () => {
  gulp.watch(paths.img.srcWatch, ser('img'))
});

task('copy-bootstrap-grid', () => {
  return gulp.src(paths.copy_bootstrap_grid.from)
    .pipe(gulp.dest(paths.copy_bootstrap_grid.dest));
});

task('clean', () => del(['build/dest']));

task('browser-sync', () => {
  browserSync.init({
    server: paths.dest,
  });

  gulp.watch(paths.destWatch).on('change', browserSync.reload);
});

task('watch', ser(
  'clean',
  par('sass', 'rigger', 'img', 'copy-bootstrap-grid'),
  par('sass-watch', 'rigger-watch', 'img-watch', 'browser-sync'))
);
