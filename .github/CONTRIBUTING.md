# Contributing Guidelines

Thank you for your interest in contributing to this project! Please read and follow these guidelines to help us maintain
a high-quality codebase.

## Code of Conduct

This project adheres to the Contributor Covenant
[code of conduct](https://github.com/yvasyliev/telegram-forwarder-bot?tab=coc-ov-file#readme). By participating, you
are expected to uphold this code. Please report unacceptable behavior to ye.vasyliev@gmail.com.

## Using GitHub Issues

We use GitHub issues to track questions, bugs and enhancements.

If you are reporting a bug, please help to speed up problem diagnosis by providing as much information as possible.
Ideally, that would include a small sample project that reproduces the problem.

## Reporting Security Vulnerabilities

If you think you have found a security vulnerability in the project please *DO NOT* disclose it publicly until we've had
a chance to fix it. Please don't report security vulnerabilities using GitHub issues, instead head over to
[Security policy](https://github.com/yvasyliev/telegram-forwarder-bot?tab=security-ov-file#readme) and learn how to
disclose them responsibly.

## Code Conventions and Housekeeping

* Please respect the project's code style. Check the [checkstyle.xml](../config/checkstyle/checkstyle.xml) file for the
  checkstyle configuration.
* Make sure that your contribution embeds well into the already existing code. For example, the unified folder/package
  structure must be used wherever possible.
* Document your source code! It's easy because one can most likely just stick to the online API reference. This is a
  requirement. Not because your contributed source code is unreadable. Instead, the aim is to keep the Javadoc complete
  and - accompanying this - to keep this project as accessible as possible to developers like yourself. Please ensure
  you have documented all public classes, methods, and fields in your contribution.
* Unit tests help to assure that functionality works. Please ensure that your contribution is covered by unit tests.
* Make sure that your contribution does not break the build. You can check this by running the following command:

  ```bash
  ./gradlew build
  ```

  If you are on Windows, use `gradlew.bat` instead of `./gradlew`.

## Contribution Flow

1. Create a fork from this repository.
2. Create a branch in your fork in which you develop your contribution (one branch per feature/fix).
3. Create meaningful and well-separated commits.
4. Make sure your contribution follows the contribution guidelines above.
5. Create a pull request from your feature branch to the <ins>default</ins> branch of this project.

**Do not commit secrets** such as passwords, API keys, or credentials.

---

Thank you for helping us improve this project!
