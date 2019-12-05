using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Connect22.Controllers.Hello {
    [ApiController]
    [Route("api/[controller]/[action]")]
    [Authorize]
    public class HelloController : ControllerBase {

        [HttpGet]
        public string Hello() => "Hello";

    }
}