# Add any ProGuard configurations specific to this
# extension here.

-keep public class com.bextdev.MarkdownNotifier.MarkdownNotifier {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'com/bextdev/MarkdownNotifier/repack'
-flattenpackagehierarchy
-dontpreverify
