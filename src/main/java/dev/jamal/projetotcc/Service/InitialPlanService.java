//package dev.jamal.projetotcc.Service;
//
//import dev.jamal.projetotcc.DTO.Response.InitialPlanResponseDTO;
//import dev.jamal.projetotcc.Entities.Hobby;
//import dev.jamal.projetotcc.Entities.User;
//import dev.jamal.projetotcc.Entities.RecommendationProfile;
//import dev.jamal.projetotcc.Repository.HobbyRepository;
//import dev.jamal.projetotcc.Repository.UserRepository;
//import dev.jamal.projetotcc.Service.AI.AIprovider;
//import dev.jamal.projetotcc.Service.AI.AIContextService;
//import org.springframework.stereotype.Service;
//
//@Service
//public class InitialPlanService {
//
//    private final HobbyRepository hobbyRepository;
//    private final UserRepository userRepository;
//    private final AIprovider aiprovider;
//    private final PromptBuilderService promptBuilderService;
//
//    public InitialPlanService(HobbyRepository hobbyRepository, UserRepository userRepository, AIprovider aiprovider, PromptBuilderService promptBuilderService) {
//        this.hobbyRepository = hobbyRepository;
//        this.userRepository = userRepository;
//        this.aiprovider = aiprovider;
//        this.promptBuilderService = promptBuilderService;
//    }
//
//    public InitialPlanResponseDTO gerarPlanoInicial(
//            Long userId , Long hobbyId
//    ) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("Usuário não encontrado")
//                );
//        Hobby hobby = hobbyRepository.findById(hobbyId)
//                .orElseThrow(() -> new RuntimeException("Hobby não encontrado"));
//
//        RecommendationProfile profile = user.getProfile();
//
//        if (profile == null) {
//            throw new RuntimeException(
//                    "O usuário não possui um perfil cadastrado"
//            );
//        }
//
////        AIContextService context = new AIContextService(
////                user,
////                profile,
////                hobby,
////
////        );
//
////        String prompt=
////                promptBuilderService.buildInitialPlanPrompt(context);
//
////        String planoInicial = aiprovider.generate(prompt);
//
////        return new InitialPlanResponseDTO(
////                hobby.getId(),
////                hobby.getNome(),
////                planoInicial
////        );
//
////    }
//}
