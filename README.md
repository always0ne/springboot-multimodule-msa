# springboot-multimodule-msa
## Index
  - [Overview](#overview) 
  - [Getting Started](#getting-started)
  - [Contributing](#contributing)
  - [Authors](#authors)
  - [License](#license)
  - [Deployment](#deployment)
  - [Used or Referenced Projects](Used-or-Referenced-Projects)

## About springboot-multimodule-msa
멀티모듈로 MSA 서버를 구축할 수 있도록 하는 템플릿 프로젝트입니다.

## 시스템 구조
### api - *
어플리케이션 레이어입니다.  
접근제어, 컨트롤러, request/response 를 관리합니다.
### domain - *
도메인 레이어입니다.  
모델, 레포지터리, dto, 서비스들을 관리합니다.
### core
서버 전반적으로 사용하는 기능을 포함합니다.

### CI/CD
파일의 diff에 트리거를 두어 해당 커밋에 변경된 모듈만 CI/CD가 이루어지도록 하였습니다. 

## Getting Started
### Dependencies
- JDK11
- All dependencies are managed by gradle

## Contributing

If you see this project and have any points to improve or do not understand, please post any issues or Pull Requests.
Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code
of conduct, and the process for submitting pull requests to us.

## Authors
  - [Always0ne](https://github.com/Always0ne) - **SangIl Hwang** - <si8363@soongsil.ac.kr>

See also the list of [contributors](https://github.com/always0ne/springboot-multimodule-msa/contributors)
who participated in this project.
## Used or Referenced Projects
 - [spring-logback-slack-notification-example](https://github.com/brant-hwang/spring-logback-slack-notification-example) - **LICENSE ?** 
    - referenced to create an error notification function.

## License
```
MIT License

Copyright (c) 2020 always0ne

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
