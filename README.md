# kubernetes-client
![GitHub License](https://img.shields.io/github/license/Vi-Nk/kubernetes-client)

This project is a **Java-based Kubernetes Client** created as part of my self-learning to understand Kubernetes client-server infrastructure and other core components. It focuses on implementing core functionalities required to interact with Kubernetes clusters, such as authentication, configuration parsing, and API communication.

## Implementations Goals

- [x] Parsing Kubernetes configuration file (`kubeconfig`) for connection.
- [x] Establishing secure TLS connections to Kubernetes clusters.
- [x] Basic HTTPs request to Kubernetes API endpoint.
- [x] GET resource details (e.g., Pods, Namespaces).
- [ ] Add support for other HTTP methods (POST, PUT, DELETE).
- [ ] coreV1 API support.
- [ ] Parsing different structure of kubeconfig elements for connection(e.g : RSA client keys, token based auth..).
- [ ] Implement resource creation and deletion (e.g., Pods, Deployments).
- [ ] Add support for watching Kubernetes resources using WebSockets.
- [ ] Improve error handling and logging mechanisms.
- [ ] Provide examples for advanced use cases (e.g., RBAC, custom resources).
- [ ] Package the client as a reusable library.

and so on...
