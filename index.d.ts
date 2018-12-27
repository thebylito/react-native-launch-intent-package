declare module "react-native-launch-intent-package" {
  interface PackageOptions {
    packageName: string;
  }

  export class PackageIntentAndroid {
    static canStartIntent(
      packageOptions: PackageOptions
    ): Promise<PackageOptions>;
    static startIntent(packageOptions: PackageOptions): Promise<void>;
  }
}
