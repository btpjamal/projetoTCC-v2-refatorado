import { useEffect, useState } from "react";
import { api } from "../../api/api";
import { useNavigate } from "react-router";


export default function DiscoverTab() {

    const navigate = useNavigate();

    const [recommendations, setRecommendations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState("");
    const nome = localStorage.getItem("nome");

     useEffect(() => {
       async function carregarRecomendacoes() {
         try {
           setLoading(true);

           const token = localStorage.getItem("token");
           const userId = localStorage.getItem("userId");

           if (!token || !userId) {
               localStorage.clear();
               navigate("/login");
               return;
           }

           const response = await api.get(`/recommendations/${userId}`, {
               headers: {
                 Authorization: `Bearer ${token}`,
               },
             },
           );

           console.log("Recomendações recebidas:", response.data);
           setRecommendations(response.data);
         } catch (error) {
           console.error(error);
           console.error("status:", error.response?.status);
           console.error("Resposta:", error.response?.data);

             if (error.response?.status === 401 || error.response?.status === 403) {
                 localStorage.clear();
                 navigate("/login");
                 return;
             }

           if (error.response?.status === 404) { navigate("/profile"); return; }
           setErro("Não foi possível carregar as recomendações");
         } finally {
           setLoading(false);
         }
       }

       async function registrarFeedback(hobbyId, tipo) {
           try {
               const userId = localStorage.getItem("userId");
               const token = localStorage.getItem("token");

               await api.post(
                   `/recommendation-feedbacks/${userId}/${hobbyId}`,
                   { tipo },
                   {
                       headers: {
                           Authorization: `Bearer ${token}`,
                       },
                   }
               );

               setRecommendations((atuais) =>
                   atuais.filter((r) => r.hobbyId !== hobbyId)
               );

           } catch (error) {
               console.error("Erro ao registrar feedback:", error);
           }
       }

       carregarRecomendacoes();
     }, [navigate]);


     return (
             <section>
                 <h2>Descobrir hobbies</h2>

                 <div className="recommendations-grid">

                     {recommendations.map((recommendation) => (

                         <div
                             key={recommendation.hobbyId}
                             className="recommendation-card"
                             onClick={() =>
                                 navigate(
                                     `/recommendations/${recommendation.hobbyId}`
                                 )
                             }
                         >
                             <h3>
                                 {recommendation.nome}
                             </h3>

                             <p>
                                 {recommendation.descricao}
                             </p>

                             <p>
                                 <strong>Categoria:</strong>{" "}
                                 {recommendation.categoria}
                             </p>

                             <p>
                                 <strong>Score:</strong>{" "}
                                 {recommendation.score}
                             </p>

                             {recommendation.motivos?.length > 0 && (
                                 <div>
                                     <strong>
                                         Por que recomendamos:
                                     </strong>

                                     <ul>
                                         {recommendation.motivos.map(
                                             (motivo, index) => (
                                                 <li key={index}>
                                                     {motivo}
                                                 </li>
                                             )
                                         )}
                                     </ul>
                                 </div>
                             )}

                             {recommendation.alertas?.length > 0 && (
                                 <div>
                                     <strong>
                                         Pontos a considerar:
                                     </strong>

                                     <ul>
                                         {recommendation.alertas.map(
                                             (alerta, index) => (
                                                 <li key={index}>
                                                     {alerta}
                                                 </li>
                                             )
                                         )}
                                     </ul>
                                 </div>
                             )}

                             <div>
                                 <button
                                     type="button"
                                     onClick={(e) => {
                                         e.stopPropagation();

                                         registrarFeedback(
                                             recommendation.hobbyId,
                                             "NAO_INTERESSADO"
                                         );
                                     }}
                                 >
                                     Não me interessa
                                 </button>

                                 <button
                                     type="button"
                                     onClick={(e) => {
                                         e.stopPropagation();

                                         registrarFeedback(
                                             recommendation.hobbyId,
                                             "INTERESSADO"
                                         );
                                     }}
                                 >
                                     Tenho interesse
                                 </button>
                             </div>

                         </div>
                     ))}

                 </div>
             </section>
         );
}